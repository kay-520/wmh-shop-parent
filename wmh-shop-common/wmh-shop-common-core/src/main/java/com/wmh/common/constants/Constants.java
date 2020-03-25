package com.wmh.common.constants;

public interface Constants {
    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";
    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fail";

    Integer NOT_FOUND = 404;
    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;
    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;
    String MOBILE_ISEXIST = "mobile is exist!";
    String REGISTER_SUCCESS = "register success!";
    String REGISTER_ERROR = "register error!";
    String UPDATE_SUCCESS = "update success!";
    String UPDATE_ERROR = "update success!";
    String MOBILE_OR_PASSWORD_ERROR = "mobile or password is error!";
    String SALT = "user";
    String TOKEN_IS_NULL = "token is null";
    String TOKE_INVALID = "token is invalid or expired";
    String CHANNEL_ERROR = "channel is error!";
    String DEVICEINFOR_IS_NULL = "deviceInfor is null!";
    //二维码时效
    Integer QR_CODE_EXPIRE_SECONDS = 18000;
    //生成二维码失败
    String QR_GEN_ERROR = "Failed to generate QR code!";
    //存入redis中的前缀
    String MSG_PRE = "smscode_";

    String UNIONLOGIN = "member.unionLogin";
    String UNIONLOG_CALLBACK = "member.unionLoginCallback.";
    String OPENID_IS_NULL = "openId is null";
    String TOKENVAUE_IS_NULL = "tokenValue is not exist Or error";
    String OPENID = "openId";
    String UNIONPUBLICID = "unionPublicId";
    String USER_NOT_BOUND = "The user has not bound an account, please associate the account";
}
