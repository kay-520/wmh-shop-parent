package com.wmh.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.common.util.DesensitizationUtil;
import com.wmh.common.util.MD5Util;
import com.wmh.common.util.RedisUtil;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.api.dto.req.UserMsgRegisterDto;
import com.wmh.member.api.dto.req.UserRegisterDto;
import com.wmh.member.api.dto.resp.UserRespDto;
import com.wmh.member.api.service.MemberService;
import com.wmh.member.domain.UserDo;
import com.wmh.member.domain.UserLoginLogDo;
import com.wmh.member.fegin.WeChatServiceFegin;
import com.wmh.member.service.UserService;
import com.wmh.member.service.impl.UserAsyncLogComponent;
import com.wmh.member.utils.ChannelUtils;
import com.wmh.wechat.api.dto.LoginTemplateDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by wangmh
 * @name: MemberController.java
 * @description: 会员接口
 * @date:2020/3/3
 **/
@RestController
//@RefreshScope //刷新配置中心
public class MemberController extends BaseApiService implements MemberService {
    @Value("${wmh.qUrlPre}")
    private String qUrlPre;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ChannelUtils channelUtils;

    @Autowired
    private UserAsyncLogComponent userAsyncLogComponent;

    @Autowired
    private WeChatServiceFegin weChatServiceFegin;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /***
     * 会员注册接口
     * @param userRegisterDto
     * @return
     */
    @Override
    public BaseResponse<JSONObject> register(UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return setResultError(Constants.NOT_FOUND, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryWrapper<UserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserDo::getMobile, userRegisterDto.getMobile());
        //验证手机号是否存在
        UserDo userDo = userService.getOne(queryWrapper, true);
        if (userDo != null) {
            return setResultError(Constants.MOBILE_ISEXIST);
        }
        //dto->do
        UserDo userDo1 = dtoToDo(userRegisterDto, UserDo.class);
        //MD5加密
        userDo1.setPassword(MD5Util.MD5(userDo1.getPassword()));
        userDo1.setAge(18L);
        userDo1.setCreateTime(new Date());
        userDo1.setIsAvalible(1L);
        userDo1.setSex(1L);
        boolean flag = userService.save(userDo1);
        return setResultFlag(flag, Constants.REGISTER_SUCCESS, Constants.REGISTER_ERROR);
    }

    @Override
    public BaseResponse<JSONObject> registerByMsg(UserMsgRegisterDto userMsgRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return setResultError(bindingResult.getFieldError().getDefaultMessage());
        }
        String code = userMsgRegisterDto.getCode();
        String mobile = userMsgRegisterDto.getMobile();
        if (!code.equals(redisUtil.getString(Constants.MSG_PRE + mobile))) {
            return setResultError("code is error!");
        }
        UserDo userDo = new UserDo();
        userDo.setMobile(mobile);
        userDo.setPassword(MD5Util.MD5(userMsgRegisterDto.getPassword()));
        userDo.setAge(18L);
        userDo.setCreateTime(new Date());
        userDo.setIsAvalible(1L);
        userDo.setSex(1L);
        if (userService.save(userDo)) {
            //删除key
            redisUtil.delKey(Constants.MSG_PRE + mobile);
            return setResultError(Constants.REGISTER_SUCCESS);
        }
        return setResultError(Constants.REGISTER_ERROR);
    }


    /***
     * 会员登录接口
     * @param userRegisterDto
     * @return
     */
    @Override
    public BaseResponse<JSONObject> login(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult,
                                          String sourceIp, String channel, String deviceInfor) {
        if (bindingResult.hasErrors()) {
            return setResultError(bindingResult.getFieldError().getDefaultMessage());
        }
        QueryWrapper<UserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserDo::getMobile, userRegisterDto.getMobile())
                .eq(UserDo::getPassword, MD5Util.MD5(userRegisterDto.getPassword()));
        //验证手机号和密码是否正确
        UserDo userDo = userService.getOne(queryWrapper, true);
        if (userDo == null) {
            return setResultError(Constants.MOBILE_OR_PASSWORD_ERROR);
        }
        //验证登录类型
        if (!channelUtils.existChannel(channel)) {
            return setResultError(Constants.CHANNEL_ERROR);
        }
        //验证登录设备是否为空
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError(Constants.DEVICEINFOR_IS_NULL);
        }
        //生成token
        String token = tokenUtils.createToken(Constants.SALT, userDo.getId().toString(), 3000L);
        //若存在微信openId，发送微信公众模板信息
        if (!StringUtils.isEmpty(userDo.getWxOpenId())) {
            weChatServiceFegin.sendLoginTemplate(new LoginTemplateDto(DesensitizationUtil.mobileEncrypt(userDo.getMobile()), new Date(), sourceIp, deviceInfor, userDo.getWxOpenId()));
        }
        UserLoginLogDo userLoginLogDo = new UserLoginLogDo(userDo.getId(), sourceIp, new Date(), token, channel, deviceInfor);
        //生成登录日志
        userAsyncLogComponent.loginLog(userLoginLogDo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userToken", token);
        BaseResponse<Object> qrUrl = weChatServiceFegin.getQrUrl(userDo.getId());
        String ticket = "";
        if (qrUrl.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            ticket = qrUrl.getData().toString();
        }
        jsonObject.put("qrUrl", ticket.equals("") ? "" : qUrlPre + ticket);
        return setResultSuccess(jsonObject);
    }

    /***
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @Override
    public BaseResponse<JSONObject> login(String token) {
        if (StringUtils.isEmpty(token)) {
            return setResultError(Constants.TOKEN_IS_NULL);
        }
        //从redis中根据token获取用户id
        String tokenValue = tokenUtils.getTokenValue(token);
        if (StringUtils.isEmpty(tokenValue)) {
            return setResultError(Constants.TOKE_INVALID);
        }
        long userId = Long.parseLong(tokenValue);
        UserDo byId = userService.getById(userId);
        UserRespDto userRespDto = doToDto(byId, UserRespDto.class);
        userRespDto.setMobile(DesensitizationUtil.mobileEncrypt(userRespDto.getMobile()));
        return setResultSuccess(userRespDto);
    }

    /***
     * 通过微信openid查询用户是否关注公众号
     * @param openId
     * @return
     */
    @Override
    public BaseResponse<UserRespDto> selectAndUpdateByOpenId(long userId, String openId) {
        UserDo userDo = userService.getOne((new QueryWrapper<UserDo>()).lambda().eq(UserDo::getWxOpenId, openId));
        if (userDo == null) {
            UpdateWrapper<UserDo> wrapper = new UpdateWrapper<>();
            wrapper.lambda().eq(UserDo::getId, userId);
            userService.update(new UserDo(openId), wrapper);
            return setResultSuccess(Constants.UPDATE_SUCCESS);
        }
        return setResultError(Constants.UPDATE_ERROR);
    }

    /***
     * 根据openId查询是否存在
     * @param openId
     * @return
     */
    @Override
    public BaseResponse<JSONObject> selectByOpenId(String openId) {
        UserDo userDo = userService.getOne((new QueryWrapper<UserDo>()).lambda().eq(UserDo::getWxOpenId, openId));
        if (userDo == null) {
            return setResultSuccess(Constants.UPDATE_SUCCESS);
        }
        return setResultError(Constants.UPDATE_ERROR);
    }

    /***
     * 通过openId取消关联
     * @param openId
     * @return
     */
    @Override
    public BaseResponse<JSONObject> updateByOpenId(String openId) {
        UpdateWrapper<UserDo> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(UserDo::getWxOpenId, openId);
        boolean update = userService.update(new UserDo(""), wrapper);
        if (update) {
            return setResultSuccess("update success!");
        }
        return setResultError("update error!");
    }

    @Override
    public BaseResponse<JSONObject> sendMsg(Long mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("mobile is null!");
        }
        QueryWrapper<UserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserDo::getMobile, mobile);
        if (userService.getOne(queryWrapper) != null) {
            return setResultError("Mobile number has been registered!");
        }
        int code = Integer.parseInt(RandomStringUtils.randomNumeric(6));
        int min = 100000;//最小
        if (code < min) {
            code = code + min;
        }
        System.out.println("生成的验证码是：" + code);
        //2.将验证码存入redis中
        redisUtil.setString(Constants.MSG_PRE + mobile, code + "", 60L);//一分钟过期
        //3.将验证码和手机号发送到rabbitMQ中
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile + "");
        map.put("code", code + "");
        //现在mq中创建路由key：sms
        rabbitTemplate.convertAndSend("sms", map);
        return setResultSuccess("msg send success!");
    }
}
