package com.wmh.wechat.mp.handler;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.wechat.fegin.MemberServiceFegin;
import com.wmh.wechat.mp.builder.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    MemberServiceFegin memberServiceFegin;

    /***
     * 取消关注公众号
     * @param wxMessage
     * @param context
     * @param wxMpService
     * @param sessionManager
     * @return
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        //获取用户openId
        String openId = wxMessage.getFromUser();
        this.logger.info("取消关注用户 OPENID: " + openId);
        //将openId与用户取消关联
        BaseResponse<JSONObject> jsonObjectBaseResponse = memberServiceFegin.updateByOpenId(openId);
        if (jsonObjectBaseResponse.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return new TextBuilder().build("Unsubscribe success!", wxMessage, wxMpService);
        }
        return new TextBuilder().build("Unsubscribe error!", wxMessage, wxMpService);
    }
}
