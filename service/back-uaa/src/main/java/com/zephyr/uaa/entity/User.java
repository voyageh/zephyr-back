package com.zephyr.uaa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zephyr.base.constant.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@TableName("basic_user")
public class User extends BaseEntity {
    @TableField("user_name")
    private String userName;
    private String password;
    private String tel;
    private String email;
}
