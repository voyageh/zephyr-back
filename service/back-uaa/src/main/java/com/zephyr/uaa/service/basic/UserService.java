package com.zephyr.uaa.service.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zephyr.base.exception.BizException;
import com.zephyr.base.utils.RedisUtils;
import com.zephyr.security.utils.JWTUtils;
import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.dto.request.basic.LoginDTO;
import com.zephyr.uaa.entity.User;
import com.zephyr.uaa.mapper.basic.UserMapper;
import com.zephyr.uaa.mapstruct.MapperUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtils redisUtils;
    private final MapperUser mapperUser;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, RedisUtils redisUtils, MapperUser mapperUser) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.redisUtils = redisUtils;
        this.mapperUser = mapperUser;
    }

    public User getUserByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    public HashMap<String, Object> Login(LoginDTO dto) {
        User user = getUserByUserName(dto.getUserName());
        if (user == null) {
            throw new BizException("用户名不存在！");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException("密码不正确！");
        }
        if (user.getStatus().equals("2")) {
            throw new BizException("帐号已被禁用！");
        }
        // 查询权限
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        redisUtils.set(user.getUserName(), "123");
        return JWTUtils.createToken(user.getUserName());
    }

    public User register(CreateUserDTO dto) {
        User user = getUserByUserName(dto.getUserName());
        if (user != null) {
            throw new BizException("用户名已存在！");
        }
        // 密码加密
//        user = mapperUser.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userMapper.insert(user);
        return user;
    }
}
