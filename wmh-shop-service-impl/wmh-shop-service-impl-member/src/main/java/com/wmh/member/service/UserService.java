package com.wmh.member.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.doentity.UserDo;
import com.wmh.member.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: UserService.java
 * @description: 用户Service层
 * @date:2020/3/13
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, UserDo> {
}
