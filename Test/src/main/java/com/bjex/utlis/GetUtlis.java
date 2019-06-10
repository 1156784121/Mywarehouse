package com.bjex.utlis;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUtlis {

    public static Map<String,String> getUtil(String Url, List<NameValuePair> param){

        Url = Config.BaseAddress+Url;

        Map<String,String> result = new HashMap<String, String>();
//        创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        传入参数
        URI uri = null;
        try {
            uri = new URIBuilder(Url).setParameters(param).build();
        } catch (URISyntaxException e) {
            LoggerTool.logger("创建 URI 类异常，请检查参数是否正确");
            result.put("ResultCode","1");
            return result;
        }
//        创建Http get对象
        HttpGet httpGet = new HttpGet(uri);

//        设置请求头
        for (Object o :
                Config.RequestHeader.keySet()) {
            httpGet.setHeader(String.valueOf(o),String.valueOf(Config.RequestHeader.get(o)));
        }

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.valueOf(Config.ResponseTime)).setConnectTimeout(Integer.valueOf(Config.ResponseTime)).build();
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            LoggerTool.logger("请求异常，请检查各项参数是否正确");
            result.put("ResultCode","1");
            return result;
        }
        String resultStr = null;
        try {
            resultStr = EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (IOException e) {
            LoggerTool.logger("获取请求结果异常");
            result.put("ResultCode","1");
            return result;
        }

        result.put("result",resultStr);
        result.put("StatusCode",String.valueOf(response.getStatusLine().getStatusCode()));
        result.put("ResultCode","0");

        return result;

    }
}
