package com.zephyr.uaa.controller.basic;

import com.zephyr.base.constant.ResultDTO;;
import com.zephyr.uaa.dto.request.basic.CreateUserDTO;
import com.zephyr.uaa.dto.request.basic.LoginDTO;
import com.zephyr.uaa.service.basic.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "user", tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@Slf4j
@Validated
public class UserController {

    @Value("${server.port}")
    private String prot;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResultDTO<?> register(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return ResultDTO.success(userService.register(createUserDTO), "注册成功");
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResultDTO<?> login(@RequestBody @Validated LoginDTO loginDTO) {
        return ResultDTO.success(userService.Login(loginDTO));
    }

    @GetMapping("/hello")
    public String hello() {
        return this.prot;
    }
}
