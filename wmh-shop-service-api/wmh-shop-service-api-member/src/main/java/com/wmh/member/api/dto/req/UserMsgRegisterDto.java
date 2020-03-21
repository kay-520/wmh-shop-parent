package com.wmh.member.api.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by wangmh
 * @name: UserMsgRegisterDto.java
 * @description:
 * @date:2020/3/21
 **/
@Data
@ApiModel("短信注册接口")
public class UserMsgRegisterDto {
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

    /***
     * 短信验证码
     */
    @NotNull(message = "code is null")
    @ApiModelProperty(value = "验证码", name = "code", required = true)
    private String code;
}
