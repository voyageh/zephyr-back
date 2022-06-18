package com.zephyr.uaa.mapper.basic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zephyr.uaa.entity.basic.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}