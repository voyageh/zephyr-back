package com.zephyr.uaa.controller.basic;

import com.zephyr.base.constant.ResultDTO;
import com.zephyr.base.utils.RedisUtils;
import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.dto.request.basic.LoginDTO;
import com.zephyr.uaa.service.basic.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private RedisUtils redisUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RedisUtils redisUtils) {
        this.userService = userService;
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
        HashMap<String, Object> token = userService.login(loginDTO.getUserName(), loginDTO.getPassword());
        return new ResultDTO<>("200", token);
    }

    @GetMapping("/hello")
    public String hello() {
        redisUtils.set("user", "kkkk");
        return this.prot;
    }
}
