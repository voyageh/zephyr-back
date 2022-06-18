package com.zephyr.uaa.controller.basic;

import com.zephyr.base.constant.ReturnResultDTO;
import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.dto.request.basic.LoginDTO;
import com.zephyr.uaa.service.basic.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public String register(@RequestBody @Validated CreateUserDTO createUserDTO) {
        log.info("createUserDTO", createUserDTO);
        return "sssssss";
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ReturnResultDTO<?> login(@RequestBody @Validated LoginDTO loginDTO) {
        HashMap<String, Object> token = userService.login(loginDTO.getUserName(), loginDTO.getPassword());
        return new ReturnResultDTO("200", token);
    }

    @GetMapping("/hello")
    public String hello() {
        redisTemplate.opsForValue().set("user", "kkkk");
        return this.prot;
    }
}
