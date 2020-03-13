package com.wmh.member.api.dto.resp;

import lombok.Data;

import java.util.Date;

/**
 * @author: create by wangmh
 * @name: UserRegisterDto.java
 * @description: 用户注册响应DTO
 * @date:2020/3/13
 **/
@Data
public class UserRespDto {
  
    /**
     * 手机号码
     */

    private String mobile;
    /**
     * 邮箱
     */

    private String email;

    /**
     * 用户名称
     */

    private String userName;
    /**
     * 性别 0 男 1女
     */

    private long sex;
    /**
     * 年龄
     */

    private Long age;


    /**
     * 用户头像
     */
    private String picImg;

}
