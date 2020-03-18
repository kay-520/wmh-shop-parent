package com.wmh.wechat.fegin;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.member.api.dto.resp.UserRespDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: create by wangmh
 * @name: MemberServiceFegin.java
 * @description: 调用会员服务接口
 * @date:2020/3/17
 **/
@FeignClient("wmh-member")
public interface MemberServiceFegin {
    @GetMapping("updateByOpenId")
    BaseResponse<JSONObject> updateByOpenId(@RequestParam String openId);

    @GetMapping("selectAndUpdateByOpenId")
    BaseResponse<UserRespDto> selectAndUpdateByOpenId(@RequestParam long userId, @RequestParam String openId);

    @GetMapping("selectByOpenId")
    BaseResponse<JSONObject> selectByOpenId(@RequestParam String openId);
}
