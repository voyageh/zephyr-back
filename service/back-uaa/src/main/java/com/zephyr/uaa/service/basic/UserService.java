package com.zephyr.uaa.service.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zephyr.base.exception.ValidationException;
import com.zephyr.security.utils.JWTUtils;
import com.zephyr.uaa.entity.basic.User;
import com.zephyr.uaa.mapper.basic.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    protected UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User loadUserByUsername(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    public HashMap<String, Object> login(String userName, String password) {
        User user = loadUserByUsername(userName);
        if (user == null) {
            throw new ValidationException("用户名不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("密码不正确");
        }
        if (user.getStatus().equals("2")) {
            throw new ValidationException("帐号已被禁用");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return JWTUtils.createToken(userName);
    }
}
