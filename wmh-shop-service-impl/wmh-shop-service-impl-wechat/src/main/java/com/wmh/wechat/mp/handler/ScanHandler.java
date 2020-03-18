package com.wmh.wechat.mp.handler;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.member.api.dto.resp.UserRespDto;
import com.wmh.wechat.fegin.MemberServiceFegin;
import com.wmh.wechat.mp.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {
    @Autowired
    private MemberServiceFegin memberServiceFegin;

    @Value("${wmh.wx.msg}")
    private String msg;

    /***
     * 用户扫码进入公众号（已关注）
     * @param wxMpXmlMessage
     * @param map
     * @param wxMpService
     * @param wxSessionManager
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        //获取openId
        String openId = wxMpXmlMessage.getFromUser();
        //用户id
        String eventKey = wxMpXmlMessage.getEventKey();
        if (!StringUtils.isEmpty(eventKey)) {
            //获取openId获取用户是否关注公众号
            BaseResponse<JSONObject> jsonObjectBaseResponse = memberServiceFegin.selectByOpenId(openId);
            if (jsonObjectBaseResponse.getCode().equals(Constants.HTTP_RES_CODE_200)) {
                return new TextBuilder().build(msg, wxMpXmlMessage, wxMpService);
            }
        }
        // 扫码事件处理
        return new TextBuilder().build(msg, wxMpXmlMessage, wxMpService);
    }
}
