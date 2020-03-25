package com.wmh.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.bean.SpringContextUtils;
import com.wmh.common.constants.Constants;
import com.wmh.common.util.RedisUtil;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.api.service.MemberUnionLoginService;
import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.domain.UserDo;
import com.wmh.member.domain.UserLoginLogDo;
import com.wmh.member.service.UnionLoginService;
import com.wmh.member.service.impl.UserAsyncLogComponent;
import com.wmh.member.strategy.UnionLoginStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserAsyncLogComponent userAsyncLogComponent;

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
        data.put("openToken", token);
        data.put("unionPublicId", unionLoginDo.getUnionPublicId());
        redisUtil.setString(token, data.toJSONString());
        return setResult(Constants.HTTP_RES_CODE_200, "success", data);
    }

    /***
     * 基于openIdToken登录
     * @param openToken
     * @return
     */
    @Override
    public BaseResponse<JSONObject> openIdLoginToken(String openToken) {
        if (StringUtils.isEmpty(openToken)) {
            return setResultError(Constants.OPENID_IS_NULL);
        }
        String tokenValue = redisUtil.getString(openToken);
        if (StringUtils.isEmpty(tokenValue)) {
            return setResultError(Constants.TOKENVAUE_IS_NULL);
        }
        JSONObject object = JSON.parseObject(tokenValue);
        String openIdValue = object.getString(Constants.OPENID);
        String unionPublicId = object.getString(Constants.UNIONPUBLICID);
        QueryWrapper<UnionLoginDo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UnionLoginDo::getUnionPublicId, unionPublicId);
        //获取beanId
        UnionLoginDo unionLoginDo = unionLoginService.getOne(wrapper);
        String unionBeanId = unionLoginDo.getUnionBeanId();
        UnionLoginStrategy unionLoginStrategy = SpringContextUtils.getBean(unionBeanId, UnionLoginStrategy.class);
        UserDo userDo = unionLoginStrategy.selectOpenId(openIdValue);
        if (userDo == null) {
            return setResultError(401, Constants.USER_NOT_BOUND);
        }
        String token = tokenUtils.createToken(Constants.SALT, userDo.getId().toString(), 3000L);
        UserLoginLogDo userLoginLogDo = new UserLoginLogDo(userDo.getId(), "192.168.75.128", new Date(), token, "ios", "第三方登录");
        //生成登录日志
        userAsyncLogComponent.loginLog(userLoginLogDo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userToken", token);
        return setResultSuccess(jsonObject);
    }
}
