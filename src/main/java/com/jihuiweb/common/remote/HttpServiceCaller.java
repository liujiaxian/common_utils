package com.jihuiweb.common.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jihuiweb.utils.SecurityKit;
import jodd.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Doaim on 2017/4/18.
 */
public class HttpServiceCaller {
    private static final Logger logger = LoggerFactory.getLogger(HttpServiceCaller.class);
    private String remoteHost;
    private String appKey;
    private String appSecret;

    /**
     * 将http请求参数转换成 JSON 对象
     * 参数名以“[]”结尾的属性将转换成数组，参数名将去掉"[]"
     * @param params
     * @return
     */
    public JSONObject parseToJsonObject(Map<String,String[]> params){
        JSONObject parameter = new JSONObject();
        params.forEach((key,value)->{
            if(value.length == 1 && !key.endsWith("]")){
                parameter.put(key,value[0]);
            }else{
                JSONArray valueArray = new JSONArray();
                valueArray.addAll(Arrays.asList(value));
                String newKey = key;
                if(newKey.endsWith("]")){
                    newKey = key.substring(0,key.length()-2);
                }
                parameter.put(newKey,valueArray);
            }
        });
        return parameter;
    }
    public ApiResponse post(String url, Map parameterObj){
        ApiResponse apiResponse = new ApiResponse();
        JSONObject parameter;
        if(parameterObj instanceof JSONObject){
            parameter = (JSONObject)parameterObj;
        }else{
            parameter = new JSONObject();
            parameter.putAll(parameterObj);
        }

        if(url.startsWith("http")||url.startsWith("https")){
            try {
                String responseStr = HttpRequest.post(url)
                        .body(parameter.toJSONString().getBytes("UTF-8"),"application/json").charset("UTF-8").send().bodyText();
                if(logger.isDebugEnabled()){
                    logger.debug("响应数据：{}",responseStr);
                }
                JSONObject response = JSONObject.parseObject(responseStr);
                apiResponse.setResponseCode(response.getString("error"));
                apiResponse.setResponseMsg(response.getString("msg"));
                apiResponse.setResponse(response.get("res"));
                apiResponse.setExtData(response.get("extData"));
            } catch (Exception e) {
                apiResponse.setResponseCode("100001");
                apiResponse.setResponseMsg(e.getMessage());
                e.printStackTrace();
            }
        }else{
            try {
                long timestamp = new Date().getTime();
                String requestSN = UUID.randomUUID().toString().replaceAll("-","");
                String sign = SecurityKit.string2MD5(appKey +appSecret+requestSN+timestamp);
                String postUrl = remoteHost + url;
                if(url.startsWith("http")){
                    postUrl = url;
                }
                String responseStr = HttpRequest.post(postUrl)
                        .header("appkey", appKey)
                        .header("requestsn",requestSN)
                        .header("timestamp",timestamp+"")
                        .header("sign",sign)
                        .body(parameter.toJSONString().getBytes("UTF-8"),"application/json").charset("UTF-8").send().bodyText();
                if(logger.isDebugEnabled()){
                    logger.debug("响应数据：{}",responseStr);
                }
                JSONObject response = JSONObject.parseObject(responseStr);
                apiResponse.setResponseCode(response.getString("code"));
                apiResponse.setResponseMsg(response.getString("msg"));
                apiResponse.setResponse(response.get("data"));
                apiResponse.setExtData(response.get("extData"));
            } catch (Exception e) {
                apiResponse.setResponseCode("100001");
                apiResponse.setResponseMsg(e.getMessage());
                e.printStackTrace();
            }
        }
        return apiResponse;
    }
    public ApiResponse get(String url){
        return get(url,new HashMap());
    }
    public ApiResponse get(String url, Map parameterObj){
        ApiResponse apiResponse = new ApiResponse();
        long timestamp = new Date().getTime();
        String requestSN = UUID.randomUUID().toString().replaceAll("-","");
        String sign = SecurityKit.string2MD5(appKey +appSecret+requestSN+timestamp);
        JSONObject parameter;
        if(parameterObj instanceof JSONObject){
            parameter = (JSONObject)parameterObj;
        }else{
            parameter = new JSONObject();
            parameter.putAll(parameterObj);
        }
        try {
            String postUrl = remoteHost + url;
            if(url.startsWith("http")){
                postUrl = url;
            }
            String responseStr = HttpRequest.get(postUrl)
                    .header("appkey", appKey)
                    .header("requestsn",requestSN)
                    .header("timestamp",timestamp+"")
                    .header("sign",sign)
                    .body(parameter.toJSONString().getBytes("UTF-8"),"application/json").charset("UTF-8").send().bodyText();
            if(logger.isDebugEnabled()){
                logger.debug("响应数据：{}",responseStr);
            }
            JSONObject response = JSONObject.parseObject(responseStr);
            apiResponse.setResponseCode(response.getString("code"));
            apiResponse.setResponseMsg(response.getString("msg"));
            apiResponse.setResponse(response.get("data"));
            apiResponse.setExtData(response.get("extData"));
        } catch (Exception e) {
            apiResponse.setResponseCode("100001");
            apiResponse.setResponseMsg(e.getMessage());
            e.printStackTrace();
        }
        return apiResponse;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
