package com.wmh.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.domain.UserDo;
import com.wmh.member.mapper.UserMapper;
import com.wmh.member.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: UserServiceImpl.java
 * @description: 用户Service层
 * @date:2020/3/13
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {
}
