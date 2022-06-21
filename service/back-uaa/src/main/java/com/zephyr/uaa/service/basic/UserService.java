package com.zephyr.uaa.service.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zephyr.base.exception.BizException;
import com.zephyr.uaa.entity.SecurityUser;
import com.zephyr.uaa.entity.User;
import com.zephyr.uaa.mapper.basic.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    public UserDetails loadUserByUsername(String userName) {
        User user = getUserByUserName(userName);
        if (user == null) {
            throw new BizException("用户名不存在！");
        }
        return new SecurityUser(user);
    }
}
