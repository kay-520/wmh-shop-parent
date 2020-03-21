package com.wmh.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.mapper.UnionLoginMapper;
import com.wmh.member.service.UnionLoginService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: UnionLoginService.java
 * @description: 联合登录service层实现
 * @date:2020/3/22
 **/
@Service
public class UnionLoginServiceImpl extends ServiceImpl<UnionLoginMapper, UnionLoginDo> implements UnionLoginService {
}
