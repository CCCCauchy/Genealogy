package com.oraro.genealogy.data.retrofit;



import android.util.Log;

import com.oraro.genealogy.data.http.HttpResult;

/**
 * 异常类
 */
public class ApiException extends RuntimeException {


    private boolean isTokenInvalid = false;

    public ApiException(HttpResult httpResult) {
        this(getApiExceptionMessage(httpResult), httpResult.getType());
    }

    public ApiException(String detailMessage, int resultType) {
        super(detailMessage);
        if (resultType == -1) {
            isTokenInvalid = true;
        }
    }

    /**
     * 对服务器接口传过来的错误信息进行统一处理
     * 免除在Activity的过多的错误判断
     */
    private static String getApiExceptionMessage(HttpResult httpResult){
        String message = "";
        Log.e("getApiExceptionMessage","httpResult.."+httpResult);
        switch (httpResult.getType()) {
            case 0:
                message = httpResult.getResultDescription();
            case 1:
                message = httpResult.getResultDescription();
                break;
            case 2:
                message = httpResult.getResultDescription();
                break;
            default:
                message = httpResult.getType()+":"+httpResult.getResultDescription();

        }
        return message;
    }

    public boolean isTokenInvalid() {
        return isTokenInvalid;
    }
}

