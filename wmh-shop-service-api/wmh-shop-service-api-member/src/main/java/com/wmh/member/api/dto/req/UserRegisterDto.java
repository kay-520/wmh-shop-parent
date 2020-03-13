package com.wmh.member.api.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by wangmh
 * @name: UserRegisterDto.java
 * @description: 用户注册请求DTO
 * @date:2020/3/13
 **/
@Data
@ApiModel
public class UserRegisterDto {
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", name = "mobile", required = true)
    @NotNull(message = "mobile is null")
    private String mobile;

    /**
     * 密码
     */
    @NotNull(message = "password is null")
    @ApiModelProperty(value = "密码", name = "passWord", required = true)
    private String password;
}
