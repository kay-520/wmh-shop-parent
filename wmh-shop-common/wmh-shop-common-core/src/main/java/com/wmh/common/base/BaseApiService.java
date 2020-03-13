package com.wmh.common.base;

import com.wmh.common.bean.BeanUtils;
import com.wmh.common.constants.Constants;
import lombok.Data;

@Data
public class BaseApiService<T> {

    public BaseResponse<T> setResultError(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    /**
     * 返回错误，可以传msg
     *
     * @param msg
     * @return
     */
    public BaseResponse<T> setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    /***
     * 返回成功，可以传data值
     * @param data
     * @return
     */
    public BaseResponse<T> setResultSuccess(T data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    /**
     * 返回成功，沒有data值
     *
     * @return
     */
    public BaseResponse<T> setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    /**
     * 返回成功，沒有data值,返回成功消息
     *
     * @return
     */
    public BaseResponse<T> setResultSuccess(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }


    /**
     * 通用封装 通用封装
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public BaseResponse<T> setResult(Integer code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

    /***
     * dto->do
     * @param dtoEntity
     * @param doClass
     * @param <Do>
     * @return
     */
    public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
        return BeanUtils.dtoToDo(dtoEntity, doClass);
    }

    /***
     * do->dto
     * @param doEntity
     * @param dtoClass
     * @param <Dto>
     * @return
     */
    public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
        return BeanUtils.doToDto(doEntity, dtoClass);
    }

    /***
     * boolean判断 返回信息
     * @param flag
     * @param successMsg
     * @param errorMsg
     * @return
     */
    public BaseResponse<T> setResultFlag(boolean flag, String successMsg, String errorMsg) {
        return flag ? setResultSuccess(successMsg) : setResultError(errorMsg);
    }

}
