package com.jihuiweb.common.remote;

public class ResponseResult extends ApiResponse {

    public static ApiResponse apiResponse = new ApiResponse();

    public static ApiResponse captchaError(){
        apiResponse.setResponseCode("100406");
        apiResponse.setResponseMsg("人机验证失败！");
        return apiResponse;
    }

    public static ApiResponse paramsError(){
        apiResponse.setResponseCode("101001");
        apiResponse.setResponseMsg("参数错误！");
        return apiResponse;
    }

    public static ApiResponse systemError(){
        apiResponse.setResponseCode("100001");
        apiResponse.setResponseMsg("系统错误！");
        return apiResponse;
    }

    public static ApiResponse requestSuccess(){
        apiResponse.setResponseCode("100000");
        apiResponse.setResponseMsg("请求成功！");
        return apiResponse;
    }

    public static ApiResponse requestError(){
        apiResponse.setResponseCode("100000");
        apiResponse.setResponseMsg("请求异常！");
        return apiResponse;
    }
}
