package com.wmh.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseApiService;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.wechat.api.service.WeChatService;
import com.wmh.wechat.mp.config.WxMpConfiguration;
import com.wmh.wechat.mp.config.WxMpProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    /***
     * 生成二维码接口
     * @param userId
     * @return
     */
    @Override
    public BaseResponse<JSONObject> getQrUrl(Long userId) {
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
            return setResultSuccess(URLEncoder.encode(ticket, "UTF-8"));
        } catch (WxErrorException | UnsupportedEncodingException e) {
            return setResultError(Constants.QR_GEN_ERROR);
        }
    }
}
