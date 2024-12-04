package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.UpdateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UpdateAccountServiceImpl implements UpdateAccountService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, String> updateUsername(Integer id, String username) {

        Map<String, String> map = new HashMap<>();

        if ("".equals(username)) {
            map.put("error_message", "修改失败，新的用户名不能为空");
            return map;
        }
        if (username.length() > 100){
            map.put("error_message", "用户名长度不能大于100");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
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
}
