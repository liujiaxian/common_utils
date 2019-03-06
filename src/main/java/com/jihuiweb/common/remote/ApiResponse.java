package com.jihuiweb.common.remote;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Doaim on 2017/4/21.
 */
public class ApiResponse {
    private String responseMsg;
    private String responseCode;
    private Object response;
    private Object extData;
    public boolean isSucceed(){
        return "100000".equals(responseCode);
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponseData() {
        return response;
    }
    public Object getExtData(){
        return extData;
    }
    public JSONObject getExtDataToJSONObject(){
        return (JSONObject) extData;
    }
    public void setExtData(Object extData){
        this.extData = extData;
    }
    public void setResponse(Object response) {
        this.response = response;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
