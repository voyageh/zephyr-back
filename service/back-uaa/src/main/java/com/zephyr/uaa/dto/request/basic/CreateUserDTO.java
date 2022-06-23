package com.zephyr.uaa.dto.request.basic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserDTO {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    public String userName;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    public String password;
    @ApiModelProperty(value = "手机号")
    public String tel;
    @ApiModelProperty(value = "邮箱")
    public String email;
}
