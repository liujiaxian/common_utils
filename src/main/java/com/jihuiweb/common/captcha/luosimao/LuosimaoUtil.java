package com.jihuiweb.common.captcha.luosimao;

import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;

public class LuosimaoUtil {

    public static final String apikey = "d4c577cc7f59bab892e86a1cf58a9cd4";

    public static final String verifyUrl = "https://captcha.luosimao.com/api/site_verify";

    public static JSONObject verify(String response){
        String responseText = HttpRequest.post(verifyUrl).form("api_key",apikey).form("response",response).send().bodyText();
        JSONObject result = JSONObject.parseObject(responseText);
        return result;
    }
}
