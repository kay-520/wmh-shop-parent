package com.wmh.wechat.mp.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.wechat.entity.WechatKeywords;
import com.wmh.wechat.mp.builder.TextBuilder;
import com.wmh.wechat.service.WechatKeywordService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@RefreshScope
@Slf4j
public class MsgHandler extends AbstractHandler {

    @Value("${wmh.wx.msg}")
    private String msg;
    @Value("${wmh.wx.weatherUrl}")
    private String weatherUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WechatKeywordService wechatKeywordService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
        String content = wxMessage.getContent();
        //1.匹配数据库
        QueryWrapper<WechatKeywords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WechatKeywords.KEYWORDNAME, content);
        WechatKeywords wechatKeywords = wechatKeywordService.getOne(queryWrapper);
        if (wechatKeywords != null) {
            String keywordValue = wechatKeywords.getKeywordValue();
            return new TextBuilder().build(StringUtils.isEmpty(keywordValue) ? msg : keywordValue, wxMessage, weixinService);
        }
        //2.调用第三方天气预报接口查询
        String replaceRpcWeatherUrl = weatherUrl.replace("####", content);
        ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity(replaceRpcWeatherUrl, JSONObject.class);
        JSONObject body = forEntity.getBody();
        if (body != null) {
            JSONArray results = body.getJSONArray("results");
            JSONObject resultsZeroJSONObject = results.getJSONObject(0);
            // 地址
            JSONObject nowJSONObject = resultsZeroJSONObject.getJSONObject("now");
            String text = nowJSONObject.getString("text");
            String temperature = nowJSONObject.getString("temperature");
            String lastUpdate = resultsZeroJSONObject.getString("last_update");
            String resultMsg = "您当前查询的城市" + content + ",天气为" + text + "天、实时温度为:" + temperature + "℃，" +
                    "最后更新的时间为:" + lastUpdate;
            return new TextBuilder().build(resultMsg, wxMessage, weixinService);
        }
        return new TextBuilder().build(msg, wxMessage, weixinService);
    }
}
