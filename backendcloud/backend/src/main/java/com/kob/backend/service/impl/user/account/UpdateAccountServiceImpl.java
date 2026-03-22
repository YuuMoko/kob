package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.UpdateAccountService;
import com.kob.backend.service.impl.utils.OssUploader;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UpdateAccountServiceImpl implements UpdateAccountService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OssUploader ossUploader;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> updateUsername(Integer id, String username) {

        Map<String, String> map = new HashMap<>();

        if ("".equals(username)) {
            map.put("error_message", "Update failed: new username cannot be empty");
            return map;
        }
        if (username.length() > 100){
            map.put("error_message", "Username length cannot exceed 100");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "Username already exists");
            return map;
        }

        User user = userMapper.selectById(id);

        user.setUsername(username);

        userMapper.updateById(user);
        map.put("error_message", "success");
        return map;
    }

    @Override
    public Map<String, String> updatePhoto(Integer id, String photo) {

        Map<String, String> map = new HashMap<>();

        User user = userMapper.selectById(id);
        user.setPhoto(photo);
        userMapper.updateById(user);
        map.put("error_message", "success");


        return map;
    }

    @Override
    public Map<String, String> uploadPhoto(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        try {
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            User user = loginUser.getUser();

            String url = ossUploader.uploadAvatar(file, user.getId());
            user.setPhoto(url);
            userMapper.updateById(user);

            map.put("error_message", "success");
            map.put("photo", url);
        } catch (Exception e) {
            map.put("error_message", e.getMessage() == null ? "upload failed" : e.getMessage());
        }
        return map;
    }

    @Override
    public Map<String, String> updatePassword(String oldPassword, String newPassword, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();

        try {
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            User user = loginUser.getUser();

            if (oldPassword == null || newPassword == null || confirmedPassword == null) {
                map.put("error_message", "Password cannot be empty");
                return map;
            }
            oldPassword = oldPassword.trim();
            if (newPassword.length() == 0 || confirmedPassword.length() == 0 || oldPassword.length() == 0) {
                map.put("error_message", "Password cannot be empty");
                return map;
            }
            if (newPassword.length() > 100 || confirmedPassword.length() > 100 || oldPassword.length() > 100) {
                map.put("error_message", "Password length cannot exceed 100");
                return map;
            }
            if (!newPassword.equals(confirmedPassword)) {
                map.put("error_message", "Passwords do not match");
                return map;
            }
            if (newPassword.equals(oldPassword)) {
                map.put("error_message", "New password must be different");
                return map;
            }

            User dbUser = userMapper.selectById(user.getId());
            if (dbUser == null) {
                map.put("error_message", "User does not exist");
                return map;
            }
            if (!passwordEncoder.matches(oldPassword, dbUser.getPassword())) {
                map.put("error_message", "Old password is incorrect");
                return map;
            }

            dbUser.setPassword(passwordEncoder.encode(newPassword));
            userMapper.updateById(dbUser);
            map.put("error_message", "success");
            return map;
        } catch (Exception e) {
            map.put("error_message", e.getMessage() == null ? "update failed" : e.getMessage());
            return map;
        }
    }
}
