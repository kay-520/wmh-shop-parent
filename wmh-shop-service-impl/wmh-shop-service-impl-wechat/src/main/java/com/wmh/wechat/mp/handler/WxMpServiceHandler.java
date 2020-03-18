package com.wmh.wechat.mp.handler;

import com.wmh.common.base.BaseResponse;
import com.wmh.common.constants.Constants;
import com.wmh.member.api.dto.resp.UserRespDto;
import com.wmh.wechat.fegin.MemberServiceFegin;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: create by wangmh
 * @name: WxMpServiceHandler.java
 * @description: 查询openId是否关联用户, 若未关联，进行关联
 * @date:2020/3/18
 **/
@Component
public class WxMpServiceHandler {
    @Autowired
    private MemberServiceFegin memberServiceFegin;

    public WxMpXmlOutMessage handler(long userId, String openId) {
        BaseResponse<UserRespDto> userRespDtoBaseResponse = memberServiceFegin.selectAndUpdateByOpenId(userId, openId);
        if (userRespDtoBaseResponse.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return null;
        }
        return null;
    }
}
