package com.wmh.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.common.util.DesensitizationUtil;
import com.wmh.common.util.MD5Util;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.api.dto.req.UserRegisterDto;
import com.wmh.member.api.dto.resp.UserRespDto;
import com.wmh.member.api.service.MemberService;
import com.wmh.member.doentity.UserDo;
import com.wmh.member.doentity.UserLoginLogDo;
import com.wmh.member.service.UserLoginLogService;
import com.wmh.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author: create by wangmh
 * @name: MemberController.java
 * @description: 会员接口
 * @date:2020/3/3
 **/
@RestController
//@RefreshScope //刷新配置中心
public class MemberController extends BaseApiService implements MemberService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserLoginLogService userLoginLogService;

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
        queryWrapper.eq(UserRegisterDto.MOBILE, userRegisterDto.getMobile());
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

    /***
     * 会员登录接口
     * @param userRegisterDto
     * @return
     */
    @Override
    public BaseResponse<JSONObject> login(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return setResultError(bindingResult.getFieldError().getDefaultMessage());
        }
        QueryWrapper<UserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserRegisterDto.MOBILE, userRegisterDto.getMobile())
                .eq(UserRegisterDto.PASSWORD, MD5Util.MD5(userRegisterDto.getPassword()));
        //验证手机号和密码是否正确
        UserDo userDo = userService.getOne(queryWrapper, true);
        if (userDo == null) {
            return setResultError(Constants.MOBILE_OR_PASSWORD_ERROR);
        }
        //生成token
        String token = tokenUtils.createToken(Constants.SALT, userDo.getId().toString());
        UserLoginLogDo userLoginLogDo = new UserLoginLogDo(userDo.getId(), "192.168.1.1", new Date(), token, "PC", "谷歌浏览器");
        //生成登录日志
        userLoginLogService.loginLog(userLoginLogDo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userToken", token);
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
//        QueryWrapper<UserDo> wrapper=new QueryWrapper<>();
//        wrapper.eq(UserDo.ID,userId);
//        UserDo byId = userService.getOne(wrapper);
        UserDo byId = userService.getById(userId);
        UserRespDto userRespDto = doToDto(byId, UserRespDto.class);
        userRespDto.setMobile(DesensitizationUtil.mobileEncrypt(userRespDto.getMobile()));
        return setResultSuccess(userRespDto);
    }
}
