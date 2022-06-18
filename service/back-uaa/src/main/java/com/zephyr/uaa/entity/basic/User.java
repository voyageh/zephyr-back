package com.zephyr.uaa.entity.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zephyr.uaa.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("basic_user")
public class User extends BaseEntity {
    @TableField("user_name")
    private String userName;
    private String password;
    private String tel;
    private String email;
}
