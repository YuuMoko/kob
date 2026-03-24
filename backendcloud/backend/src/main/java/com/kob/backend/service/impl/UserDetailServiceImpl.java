package com.kob.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user;
        try {
            user = userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            logger.error("Load user failed username={}, reason={}", username, e.getMessage(), e);
            throw new UsernameNotFoundException("Load user failed");
        }
        if (user == null) {
            logger.warn("User not found username={}", username);
            throw new UsernameNotFoundException("User does not exist");
        }
        logger.info("User loaded userId={}, username={}", user.getId(), user.getUsername());
        return new UserDetailsImpl(user);
    }
}
