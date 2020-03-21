package com.wmh.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.bean.SpringContextUtils;
import com.wmh.common.constants.Constants;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.api.service.MemberUnionLoginService;
import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.service.UnionLoginService;
import com.wmh.member.strategy.UnionLoginStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wangmh
 * @name: MemberUnionLoginController.java
 * @description: 联合登录接口
 * @date:2020/3/22
 **/
@RestController
public class MemberUnionLoginController extends BaseApiService implements MemberUnionLoginService {

    @Autowired
    private UnionLoginService unionLoginService;

    @Autowired
    private TokenUtils tokenUtils;

    /***
     * 根据不同的联合登陆id
     * @param unionPublicId
     * @return
     */
    @Override
    public BaseResponse<String> unionLogin(String unionPublicId) {
        if (StringUtils.isEmpty(unionPublicId)) {
            return setResultError("unionPublicId is null!");
        }
        QueryWrapper<UnionLoginDo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UnionLoginDo::getUnionPublicId, unionPublicId)
                .eq(UnionLoginDo::getIsAvailability, 1);
        UnionLoginDo unionLoginDo = unionLoginService.getOne(wrapper);
        if (unionLoginDo == null) {
            return setResultError("this union already close Or this union is not exist!");
        }
        String token = tokenUtils.createToken(Constants.UNIONLOGIN, "");
        String requestAddres = unionLoginDo.getRequestAddress() + "&token=" + token;
        JSONObject dataObjects = new JSONObject();
        dataObjects.put("requestAddres", requestAddres);
        return setResultSuccess(dataObjects);
    }

    /***
     * 联合登陆回调接口
     * 采用策略模式实现回调接口重构
     * @param unionPublicId
     * @return
     */
    @Override
    public BaseResponse<JSONObject> unionLoginCallback(String unionPublicId) {
        if (StringUtils.isEmpty(unionPublicId)) {
            return setResultError("unionPublicId is null");
        }
        QueryWrapper<UnionLoginDo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UnionLoginDo::getUnionPublicId, unionPublicId)
                .eq(UnionLoginDo::getIsAvailability, 1);
        UnionLoginDo unionLoginDo = unionLoginService.getOne(wrapper);
        if (unionLoginDo == null) {
            return setResultError("this union already close Or this union is not exist!");
        }
        // 获取BeanId
        String unionBeanId = unionLoginDo.getUnionBeanId();
        if (StringUtils.isEmpty(unionBeanId)) {
            return setResultError("bean is error");
        }
        //通过beanId在Spring容器中回去
        UnionLoginStrategy strategy = SpringContextUtils.getBean(unionBeanId, UnionLoginStrategy.class);
        //获取授权码
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        //通过策略模式获取openId
        String openId = strategy.unionLoginCallback(unionLoginDo, request);
        if (StringUtils.isEmpty(openId)) {
            return setResultError("openId is null OR error!");
        }
        String token = tokenUtils.createToken(Constants.UNIONLOG_CALLBACK, openId);
        JSONObject data = new JSONObject();
        data.put("openIdToken", token);
        return setResult(Constants.HTTP_RES_CODE_200, "success", data);
    }
}
