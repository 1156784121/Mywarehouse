package com.bjex.utlis;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostUtils {

        public static Map<String,String> postUtil(String Url, List<BasicNameValuePair> param){

            //            存放返回结果
            Map<String,String> map = new HashMap<String, String>();

            Url = Config.BaseAddress+Url;
//            获取HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
//            输入请求路径
            HttpPost post = new HttpPost(Url);
//            放入参数
            try {
                post.setEntity(new UrlEncodedFormEntity(param));
            } catch (UnsupportedEncodingException e) {
                LoggerTool.logger("请求参数异常，请确认参数无误");
                map.put("ResultCode","1");
                return map;
            }

//        设置请求头
            for (Object o :
                    Config.RequestHeader.keySet()) {
                post.setHeader(String.valueOf(o),String.valueOf(Config.RequestHeader.get(o)));
            }

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.valueOf(Config.ResponseTime)).setConnectTimeout(Integer.valueOf(Config.ResponseTime)).build();
            post.setConfig(requestConfig);

            CloseableHttpResponse response = null;
//            开始请求
            try {
                response = httpClient.execute(post);
            } catch (IOException e) {
                LoggerTool.logger("请求时发生异常，请确认数据各项参数无误");
                map.put("ResultCode","1");
                return map;
            }
            String resultStr="";
            try {
                resultStr = EntityUtils.toString(response.getEntity(),"UTF-8");
            } catch (IOException e) {
                LoggerTool.logger("请求时发生错误，返回数据异常");
                map.put("ResultCode","1");
                return map;
            }

            map.put("result",resultStr);
            map.put("StatusCode",String.valueOf(response.getStatusLine().getStatusCode()));
            map.put("ResultCode","0");

//            释放占用资源
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                LoggerTool.logger("请求资源释放失败");
            }

            return map;

        }



}
