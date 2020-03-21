package com.wmh.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.wechat.api.dto.LoginTemplateDto;
import com.wmh.wechat.api.service.WeChatService;
import com.wmh.wechat.mp.config.WxMpConfiguration;
import com.wmh.wechat.mp.config.WxMpProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by wangmh
 * @name: WeChatServiceImpl.java
 * @description: 微信服务实现接口
 * @date:2020/3/3
 **/
@RestController
public class WeChatController extends BaseApiService implements WeChatService {

    @Autowired
    private WxMpProperties wxMpProperties;

    @Value("${wmh.wx.loginTemplateId}")
    private String loginTemplateId;

    /***
     * 生成二维码接口
     * @param userId
     * @return
     */
    @Override
    public BaseResponse<Object> getQrUrl(Long userId) {
        if (userId == null) {
            return setResultError("userId is Null!");
        }
        try {
            //获取第一个appId
            String appId = wxMpProperties.getConfigs().get(0).getAppId();
            WxMpQrcodeService qrcodeService = WxMpConfiguration.getMpServices().get(appId).getQrcodeService();
            WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(userId.toString(), Constants.QR_CODE_EXPIRE_SECONDS);
            if (wxMpQrCodeTicket == null) {
                return setResultError(Constants.QR_GEN_ERROR);
            }
            String ticket = wxMpQrCodeTicket.getTicket();
            return setResult(Constants.HTTP_RES_CODE_200, "QrUrl create success!", URLEncoder.encode(ticket, "UTF-8"));
        } catch (WxErrorException | UnsupportedEncodingException e) {
            return setResultError(Constants.QR_GEN_ERROR);
        }
    }

    /***
     * 发送微信模板信息
     * @param loginTemplateDto
     * @return
     * 模板信息案例：亲爱的用户:{{first.DATA}} 登陆时间:{{keyword1.DATA}} 登陆ip:{{keyword2.DATA}} 登陆设备:{{keyword3.DATA}} 如果不是您本人登陆,可以联系管理员锁定账号.
     */
    @Override
    public BaseResponse<JSONObject> sendLoginTemplate(@Valid LoginTemplateDto loginTemplateDto) {
//        if (bindingResult.hasErrors()) {
//            setResultError(bindingResult.getFieldError().getDefaultMessage());
//        }
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(loginTemplateId);
        wxMpTemplateMessage.setToUser(loginTemplateDto.getOpenId());
        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", loginTemplateDto.getPhone()));
        data.add(new WxMpTemplateData("keyword1", loginTemplateDto.getLoginTime().toString()));
        data.add(new WxMpTemplateData("keyword2", loginTemplateDto.getLoginIp()));
        data.add(new WxMpTemplateData("keyword3", loginTemplateDto.getEquipment()));
        wxMpTemplateMessage.setData(data);
        try {
            String appId = wxMpProperties.getConfigs().get(0).getAppId();
            WxMpTemplateMsgService templateMsgService = WxMpConfiguration.getMpServices().get(appId).getTemplateMsgService();
            templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
            return setResultSuccess();
        } catch (Exception e) {
            return setResultError("发送失败");
        }
    }


}
