package com.kob.backend.service.impl.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.kob.backend.utils.DotEnv;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Component
public class OssUploader {

    public String uploadAvatar(MultipartFile file, Integer userId) throws Exception {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("file is empty");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("only image is allowed");
        }

        String endpoint = required("OSS_ENDPOINT");
        String bucket = required("OSS_BUCKET");
        String accessKeyId = required("OSS_ACCESS_KEY_ID");
        String accessKeySecret = required("OSS_ACCESS_KEY_SECRET");

        String prefix = DotEnv.get("OSS_AVATAR_PREFIX");
        if (prefix == null || prefix.trim().isEmpty()) prefix = "avatars";

        String ext = guessExt(file.getOriginalFilename(), contentType);
        String objectKey = String.format("%s/%s/%s/%s%s",
                prefix,
                userId,
                LocalDate.now(),
                UUID.randomUUID().toString().replace("-", ""),
                ext
        );

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try (InputStream is = file.getInputStream()) {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            meta.setContentLength(file.getSize());
            PutObjectRequest req = new PutObjectRequest(bucket, objectKey, is, meta);
            ossClient.putObject(req);
        } finally {
            try {
                ossClient.shutdown();
            } catch (Exception ignored) {
            }
        }

        String baseUrl = DotEnv.get("OSS_BASE_URL");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            String ep = endpoint.trim();
            if (ep.startsWith("https://")) ep = ep.substring("https://".length());
            if (ep.startsWith("http://")) ep = ep.substring("http://".length());
            baseUrl = "https://" + bucket + "." + ep;
        }
        if (baseUrl.endsWith("/")) baseUrl = baseUrl.substring(0, baseUrl.length() - 1);

        // objectKey 里可能含有非 ASCII（虽然我们这里不会），保险起见 encode
        String encodedKey = URLEncoder.encode(objectKey, StandardCharsets.UTF_8.name()).replace("+", "%20");
        return baseUrl + "/" + encodedKey;
    }

    private static String required(String key) {
        String v = DotEnv.get(key);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalStateException("missing config: " + key);
        }
        return v.trim();
    }

    private static String guessExt(String originalFilename, String contentType) {
        String ext = "";
        if (originalFilename != null) {
            int idx = originalFilename.lastIndexOf('.');
            if (idx >= 0 && idx < originalFilename.length() - 1) {
                ext = originalFilename.substring(idx).toLowerCase(Locale.ROOT);
            }
        }
        if (!ext.matches("^\\.(png|jpg|jpeg|gif|webp|bmp)$")) {
            if ("image/png".equalsIgnoreCase(contentType)) return ".png";
            if ("image/jpeg".equalsIgnoreCase(contentType)) return ".jpg";
            if ("image/gif".equalsIgnoreCase(contentType)) return ".gif";
            if ("image/webp".equalsIgnoreCase(contentType)) return ".webp";
            if ("image/bmp".equalsIgnoreCase(contentType)) return ".bmp";
            return "";
        }
        return ext;
    }
}

