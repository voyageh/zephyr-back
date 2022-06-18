package com.zephyr.uaa.service.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zephyr.base.utils.JWTUtils;
import com.zephyr.uaa.entity.basic.User;
import com.zephyr.uaa.mapper.basic.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    @Autowired
    protected UserMapper userMapper;

    public User getUserByUsername(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    public HashMap<String, Object> login(String userName, String password) {
        User user = getUserByUsername(userName);
        if (user == null) {
            return null;
        }
        return JWTUtils.createToken(userName);
    }
}
