package com.kob.backend.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class DotEnv {
    private static volatile Map<String, String> cached;

    private DotEnv() {
    }

    public static String get(String key) {
        String fromEnv = System.getenv(key);
        if (fromEnv != null && !fromEnv.trim().isEmpty()) return fromEnv.trim();

        Map<String, String> map = loadOnce();
        String v = map.get(key);
        return v == null ? null : v.trim();
    }

    private static Map<String, String> loadOnce() {
        if (cached != null) return cached;
        synchronized (DotEnv.class) {
            if (cached != null) return cached;
            cached = loadFromClasspath();
            return cached;
        }
    }

    private static Map<String, String> loadFromClasspath() {
        Map<String, String> map = new HashMap<>();
        try (InputStream is = DotEnv.class.getClassLoader().getResourceAsStream(".env")) {
            if (is == null) return map;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String s = line.trim();
                    if (s.isEmpty() || s.startsWith("#")) continue;
                    int idx = s.indexOf('=');
                    if (idx <= 0) continue;
                    String k = s.substring(0, idx).trim();
                    String v = s.substring(idx + 1).trim();
                    if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
                        v = v.substring(1, v.length() - 1);
                    }
                    map.put(k, v);
                }
            }
        } catch (Exception ignored) {
        }
        return map;
    }
}

