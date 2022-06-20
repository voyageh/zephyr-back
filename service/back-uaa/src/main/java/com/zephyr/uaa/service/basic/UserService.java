package com.zephyr.uaa.service.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zephyr.security.utils.JWTUtils;
import com.zephyr.uaa.entity.basic.User;
import com.zephyr.uaa.mapper.basic.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    protected UserMapper userMapper;
    private AuthenticationManager authenticationManager;

    public UserService(UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    public User getUserByUsername(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    public HashMap<String, Object> login(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        return JWTUtils.createToken(userName);
    }
}
