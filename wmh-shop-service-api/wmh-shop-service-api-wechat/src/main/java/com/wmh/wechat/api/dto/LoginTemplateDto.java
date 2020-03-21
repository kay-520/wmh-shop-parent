package com.wmh.wechat.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: create by wangmh
 * @name: LoginTemplateDto.java
 * @description:
 * @date:2020/3/21
 **/
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@ApiModel(value = "登录模板信息DTO")
public class LoginTemplateDto {
    @ApiModelProperty(value = "手机号码", name = "mobile", required = true)
    @NotNull(message = "phone is null!")
    private String phone;

    @ApiModelProperty(value = "登录时间", name = "loginTime", required = true)
    @NotNull(message = "loginTime is null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前台传数据到后台
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    @ApiModelProperty(value = "登录ip", name = "loginIp", required = true)
    @NotNull(message = "loginIp is null!")
    private String loginIp;

    @ApiModelProperty(value = "设备", name = "equipment", required = true)
    @NotNull(message = "equipment is null!")
    private String equipment;

    @ApiModelProperty(value = "openId", name = "openId", required = true)
    @NotNull(message = "openId is null")
    private String openId;
}
