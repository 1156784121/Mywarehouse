package com.bjex.utlis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjex.model.FileParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Param {

    public static List<FileParam> getParam(){
        //        获取配置文件
        String path = System.getProperty("user.dir");
        String filePath = path+ "\\Test\\config.txt";
        File file = new File(filePath);
        JSONObject config = null;

        try {
            config = JSONObject.parseObject(GetFile.readAllLineRetrnStr(file));
        }catch (Exception e){
            LoggerTool.logger("config.txt 文件转换json文件失败，请检查配置文件格式是否正确");
            System.exit(1);
        }

        JSONObject BasicInformation = JSONObject.parseObject(String.valueOf(config.get("BasicInformation")));
        Config.BaseAddress = BasicInformation.getString("BaseAddress");
        Config.ResponseTime = BasicInformation.getString("ResponseTime");

        Config.RequestHeader = JSONObject.parseObject(String.valueOf(config.get("RequestHeader")));

        String paramPath = path + "\\Test\\test.txt";
        file = new File(paramPath);
        JSONArray param = null;

       try {
           param = JSONArray.parseArray(GetFile.readAllLineRetrnStr(file));
       }catch (Exception e){
           LoggerTool.logger("test.txt 文件转换json文件失败，请检查配置文件格式是否正确");
           System.exit(1);
       }

        List<FileParam> list = new ArrayList<FileParam>();

        for (Object o : param){
            FileParam fileParam = JSONObject.parseObject(String.valueOf(o),FileParam.class);
            list.add(fileParam);
        }
        return list;
    }
}
