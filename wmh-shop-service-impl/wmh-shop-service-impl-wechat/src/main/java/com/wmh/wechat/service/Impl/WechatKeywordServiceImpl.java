package com.wmh.wechat.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.wechat.entity.WechatKeywords;
import com.wmh.wechat.mapper.WechatKeywordMapper;
import com.wmh.wechat.service.WechatKeywordService;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: WechatKeywordServiceImpl.java
 * @description:
 * @date:2020/3/21
 **/
@Service
public class WechatKeywordServiceImpl extends ServiceImpl<WechatKeywordMapper, WechatKeywords> implements WechatKeywordService {
}
