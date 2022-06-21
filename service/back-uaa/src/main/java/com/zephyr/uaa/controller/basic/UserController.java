package com.zephyr.uaa.controller.basic;

import com.zephyr.base.constant.ResultDTO;
import com.zephyr.base.exception.BizException;
import com.zephyr.base.utils.RedisUtils;
import com.zephyr.security.utils.JWTUtils;
import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.dto.request.basic.LoginDTO;
import com.zephyr.uaa.entity.User;
import com.zephyr.uaa.service.basic.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "user", tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@Slf4j
@Validated
public class UserController {

    @Value("${server.port}")
    private String prot;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RedisUtils redisUtils;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, RedisUtils redisUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.redisUtils = redisUtils;
    }


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public String register(@RequestBody @Validated CreateUserDTO createUserDTO) {
        log.info("createUserDTO", createUserDTO);
        return "sssssss";
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResultDTO<?> login(@RequestBody @Validated LoginDTO loginDTO) {
        UserDetails user = userService.loadUserByUsername(loginDTO.getUserName());
        if (user == null) {
            throw new BizException("用户名不存在！");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BizException("密码不正确！");
        }
        if (!user.isEnabled()) {
            throw new BizException("帐号已被禁用！");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return ResultDTO.success(JWTUtils.createToken(loginDTO.getUserName()));
    }

    @GetMapping("/hello")
    public String hello() {
        redisUtils.set("user", "kkkk");
        return this.prot;
    }
}
