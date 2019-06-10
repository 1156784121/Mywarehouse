package com.bjex.cases;

import com.bjex.model.FileParam;
import com.bjex.utlis.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {



    public static void main(String[] args){

        System.out.println("开始运行.....");

        List<FileParam> paramList = Param.getParam();

        for (FileParam f:
             paramList) {
            LoggerTool.logger("\n");
            LoggerTool.logger("************************************ " + f.getInterfaceCnName() +"  " +f.getRequestAddress() + " ****************************************");
            Boolean flg = false;
            if (f.getMethod().equals("POST")){
                Map<String,String> result = new HashMap<String, String>();
                Map<String,String> param = f.getParam();
                List<BasicNameValuePair> pairs = Utli.getPostParam(param);
                result = PostUtils.postUtil(f.getRequestAddress(),pairs);
                if (!result.get("ResultCode").equals("0")){
                    LoggerTool.logger(f.getInterfaceCnName() + "  接口执行失败，接口请求时发生异常");
                    continue;
                }
                flg = ComparativeResults.comparativeResults(f.getInterfaceCnName(),result,f.getReturnValue());
            }else if (f.getMethod().equals("GET")){
                    Map<String, String> result = new HashMap<String, String>();
                    Map<String,String> param = f.getParam();
                    List<NameValuePair> pairs = Utli.getGetParam(param);
                    result = GetUtlis.getUtil(f.getRequestAddress(),pairs);
                    if (!result.get("ResultCode").equals("0")){
                        LoggerTool.logger(f.getInterfaceCnName() + "  接口执行失败，接口请求时发生异常");
                        continue;
                    }
                    flg = ComparativeResults.comparativeResults(f.getInterfaceCnName(),result,f.getReturnValue());
            }
            if (flg){
                LoggerTool.logger( f.getInterfaceCnName() + " 接口执行成功，无数据错误 ");
            }else {
                LoggerTool.logger(f.getInterfaceCnName() + "  接口执行失败，请查看失败数据");
            }

        }

    }
}
