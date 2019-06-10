package com.bjex.utlis;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utli {


    /**
     * get参数请求方法
     * @param map
     * @return
     */
    public static List<NameValuePair> getGetParam(Map<String,String> map){
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String,String> entry:map.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        return pairs;
    }


    /**
     * post参数处理方法
     * @param map
     * @return
     */
    public static List<BasicNameValuePair> getPostParam(Map<String, String> map) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String,String> entry:map.entrySet()){
            pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        return pairs;
    }


    public static Boolean isNumber(String str){
        str = Utli.DeleteComma(Utli.DeleteDecimalPoint(str));
        String[] strArray = str.split("");
        for (int i = 0; i < strArray.length; i++) {

            try {
                Integer.valueOf(strArray[i]);
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }


    /**
     * 字符串去逗号
     * @param str
     * @return
     */
    public static String DeleteComma(String str){
        String[] strArray = str.split(",");
        for (int i = 0; i < strArray.length; i++) {
            if (i == 0){
                str = strArray[i];
                continue;
            }
            str += strArray[i];
        }
        return str;
    }


    /**
     * 字符串去小数点
     * @param str
     * @return
     */
    public static String DeleteDecimalPoint(String str){
        String[] strArray = str.split("\\.");
        for (int i = 0; i < strArray.length; i++) {
            if (i == 0){
                str = strArray[i];
                continue;
            }
            str += strArray[i];
        }
        return str;
    }
}
