package com.wmh.wechat.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WechatKeywords implements Serializable {
    private static final Integer DEFAULT_PID = 10;
    private Long id;
    private String keywordName;
    private String keywordValue;
    private Date createTime;
    private Date updateTime;
    private Long version;

    public static final String KEYWORDNAME="keyword_name";
}
