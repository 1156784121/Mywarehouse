package com.bjex.utlis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjex.model.ReturnValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ComparativeResults {

    public static Boolean comparativeResults( String str,Map<String,String> map, List<ReturnValue> returnValue){

        JSONObject resultJson = JSONObject.parseObject(map.get("result"));//获取返回结果

        if ( map.get("StatusCode") == null  || !map.get("StatusCode").equals("200")){
            LoggerTool.logger( str+" 返回值错误，请求状态码不为 200  StatusCode = "+ map.get("StatusCode"));
            return false;
        }

        if (resultJson.get("code") == null || (Integer)resultJson.get("code") != 0){
            LoggerTool.logger(str+" 返回值错误，状态码不为 0  code = "+ resultJson.get("code"));
            return false;
        }


        if (resultJson.get("model") != null){
            Object resultModel = resultJson.get("model");//获取返回结果
            if (resultModel instanceof JSONObject){
                return isJSONObject(str,(JSONObject) resultModel,returnValue);
            }else if (resultModel instanceof JSONArray){
                return isJSONArray(str,(JSONArray)resultModel,returnValue);
            }else if (resultModel instanceof Boolean){
                return isBoolean(str,resultModel,returnValue);
            }else if (Utli.isNumber(String.valueOf(resultModel))){
                BigDecimal a = new BigDecimal(Utli.DeleteComma(String.valueOf(resultModel)));
                return isInteger(str,a,returnValue);
            }
        }else if (resultJson.get("resultSet") != null){
            Object resultResultSet = resultJson.get("resultSet");//获取返回结果
            if (resultResultSet instanceof JSONObject){
                return isJSONObject(str,(JSONObject) resultResultSet,returnValue);
            }else if (resultResultSet instanceof JSONArray){
                return isJSONArray(str,(JSONArray)resultResultSet,returnValue);
            }else if (resultResultSet instanceof Boolean){
                return isBoolean(str,resultResultSet,returnValue);
            }else if (Utli.isNumber(String.valueOf(resultResultSet))){
                BigDecimal a = new BigDecimal(Utli.DeleteComma(String.valueOf(resultResultSet)));
                return isInteger(str,a,returnValue);
            }

        }else if (resultJson.get("data") != null){
            Object resultData = resultJson.get("data");//获取返回结果
            if (resultData instanceof JSONObject){
                return isJSONObject(str,(JSONObject) resultData,returnValue);
            }else if (resultData instanceof JSONArray){
                return isJSONArray(str,(JSONArray)resultData,returnValue);
            }else if (resultData instanceof Boolean){
                return isBoolean(str,resultData,returnValue);
            }else if (Utli.isNumber(String.valueOf(resultData))){
                BigDecimal a = new BigDecimal(Utli.DeleteComma(String.valueOf(resultData)));
                return isInteger(str,a,returnValue);
            }
        }else {
            LoggerTool.logger( str+" 返回值错误，数据模型无数据" + resultJson.get("model")+" " + resultJson.get("resultSet") + "  " + resultJson.get("data"));
            return false;
        }
        return true;
    }

    private static Boolean isInteger(String str, BigDecimal result, List<ReturnValue> returnValue) {
        for (ReturnValue r:returnValue){

            if (r.getType().equals("3")){ // type = 3 判断是否为空，并且判断返回的数字是否与对比数据一致
                if (result == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                BigDecimal value = null;

                try {
                    value = new BigDecimal(r.getValue());
                    result = result.stripTrailingZeros();
                    value = value.stripTrailingZeros();

                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+result + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                if (result.compareTo(value) != 0){
                    LoggerTool.logger(str + " 数据异常，返回数字与对比数字不一致  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            }else if (r.getType().equals("4")){ // type = 4  判断是否为空，并且判断返回的数字是否小与对比数据
                if (result == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }


                BigDecimal value = null;


                try {

                    value = new BigDecimal(r.getValue());
                    result = result.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (result.compareTo(value) != -1){
                    LoggerTool.logger(str + " 数据异常，返回数字不小于对比数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

            }else if (r.getType().equals("5")){ // type = 5  判断是否为空，并且判断返回的数字是否大与对比数据
                if (result == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }


                BigDecimal value = null;

                try {

                    value = new BigDecimal(r.getValue());
                    result = result.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (result.compareTo(value) != 1){
                    LoggerTool.logger(str + " 数据异常，返回数字不大于对比数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            } else if (r.getType().equals("6")){ // type = 6  判断是否为空，并且判断返回的数字是否大与等于对比数据
                if (result == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }


                BigDecimal value = null;

                try {
                    value = new BigDecimal(r.getValue());
                    result = result.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (!(result.compareTo(value) >= 0)){
                    LoggerTool.logger(str + " 数据异常，返回数字不大于等于对比数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            } else if (r.getType().equals("7")){ // type = 6  判断是否为空，并且判断返回的数字是否小与等于对比数据
                if (result == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }


                BigDecimal value = null;

                try {
                    value = new BigDecimal(r.getValue());
                    result = result.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+result  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (!(result.compareTo(value) <= 0)){
                    LoggerTool.logger(str + " 数据异常，返回数字不小于等于对比数字  返回数据 ：" + r.getKey() + " = "+result + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            }
        }

        return true;
    }


    private static Boolean isBoolean(String str, Object resultModel, List<ReturnValue> returnValue) {

        for (ReturnValue r : returnValue){
            if (resultModel == null){
                LoggerTool.logger( str+" 返回数据异常，返回数据为空 " + r.getKey() + " 为null" );
                return false;
            }else if (!String.valueOf(resultModel).equals(r.getValue())){
                LoggerTool.logger( str+" 返回数据异常，返回数据为空或者与对比数据不符，返回数据 " + r.getKey() + " = " + resultModel + "  对比数据 ： " +r.getKey() +" = " + r.getValue() );
                return false;
            }
        }


        return true;
    }

    private static Boolean isJSONArray(String str, JSONArray resultModel, List<ReturnValue> returnValue) {

        for (Object o: resultModel){
            JSONObject object = (JSONObject)o;

            for (ReturnValue r : returnValue){
                if (r.getType().equals("0")){
                    if (object.get(r.getKey()) == null){
                        LoggerTool.logger( str+" 数据异常，相关数据为空 " + r.getKey() + " 为null" );
                        return false;
                    }
                }else {
                    LoggerTool.logger( str+" 返回列表类型数据，type值只能为0  type = " + r.getType());
                    return false;
                }
            }
        }
        return true;
    }


    private static Boolean isJSONObject(String str, JSONObject jsonObject,List<ReturnValue> returnValue){

        for (ReturnValue r : returnValue){
            if (r.getType().equals("0")){  // type = 0  只判断该模型是否为空
                if (jsonObject.get(r.getKey()) == null){
                    LoggerTool.logger( str+" 数据异常，相关数据为空 " + r.getKey() + " 为null" );
                    return false;
                }
            }else if (r.getType().equals("1")){ // type = 1 判断是否为空，并且判断对比数据与返回数据是否一致
                if (jsonObject.get(r.getKey()) == null || !jsonObject.getString(r.getKey()).equals(r.getValue())){
                    LoggerTool.logger( str+" 数据异常，返回数据为空或者与对比数据不符，返回数据 " + r.getKey() + " = " + jsonObject.get(r.getKey()) + "  对比数据 ： " +r.getKey() +" = " + r.getValue() );
                    return false;
                }
            }else if (r.getType().equals("2")){ // type = 2 判断是否为空，并且判断返回数据中是否存在对比数据
                if (jsonObject.get(r.getKey()) == null || !jsonObject.getString(r.getKey()).contains(r.getValue())){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空或者返回字符串中不存在对比字符  返回数据 ：" + r.getKey() + " = " + jsonObject.get(r.getKey()) + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            }else if (r.getType().equals("3")){ // type = 3 判断是否为空，并且判断返回的数字是否与对比数据一致
                if (jsonObject.get(r.getKey()) == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                BigDecimal jsonInteger = null;
                BigDecimal value = null;

                try {
                    jsonInteger = new BigDecimal(jsonObject.getString(r.getKey()));
                    value = new BigDecimal(r.getValue());
                    jsonInteger = jsonInteger.stripTrailingZeros();
                    value = value.stripTrailingZeros();

                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                if (jsonInteger.compareTo(value) != 0){
                    LoggerTool.logger(str + " 数据异常，返回数字与对比数字不一致  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            }else if (r.getType().equals("4")){ // type = 4  判断是否为空，并且判断返回的数字是否小与对比数据
                if (jsonObject.get(r.getKey()) == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                BigDecimal jsonInteger = null;
                BigDecimal value = null;


                try {
                    jsonInteger = new BigDecimal(jsonObject.getString(r.getKey()));
                    value = new BigDecimal(r.getValue());
                    jsonInteger = jsonInteger.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (jsonInteger.compareTo(value) != -1){
                    LoggerTool.logger(str + " 数据异常，返回数字不小于对比数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

            }else if (r.getType().equals("5")){ // type = 5  判断是否为空，并且判断返回的数字是否大与对比数据
                if (jsonObject.get(r.getKey()) == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                BigDecimal jsonInteger = null;
                BigDecimal value = null;

                try {
                    jsonInteger = new BigDecimal(jsonObject.getString(r.getKey()));
                    value = new BigDecimal(r.getValue());
                    jsonInteger = jsonInteger.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (jsonInteger.compareTo(value) != 1){
                    LoggerTool.logger(str + " 数据异常，返回数字不大于对比数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            } else if (r.getType().equals("6")){ // type = 6  判断是否为空，并且判断返回的数字是否大与等于对比数据
                if (jsonObject.get(r.getKey()) == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                BigDecimal jsonInteger = null;
                BigDecimal value = null;

                try {
                    jsonInteger = new BigDecimal(jsonObject.getString(r.getKey()));
                    value = new BigDecimal(r.getValue());
                    jsonInteger = jsonInteger.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (!(jsonInteger.compareTo(value) >= 0)){
                    LoggerTool.logger(str + " 数据异常，返回数字不大于等于对比数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            } else if (r.getType().equals("7")){ // type = 6  判断是否为空，并且判断返回的数字是否小与等于对比数据
                if (jsonObject.get(r.getKey()) == null ){
                    LoggerTool.logger(str + " 数据异常，返回字符串为空  返回数据 ：" + r.getKey() + " =  null"  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }

                BigDecimal jsonInteger = null;
                BigDecimal value = null;

                try {
                    jsonInteger = new BigDecimal(jsonObject.getString(r.getKey()));
                    value = new BigDecimal(r.getValue());
                    jsonInteger = jsonInteger.stripTrailingZeros();
                    value = value.stripTrailingZeros();
                }catch (Exception e){
                    LoggerTool.logger(str + " 数据异常，类型转换失败，请确认返回数据或者对比数据为正常数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
                if (!(jsonInteger.compareTo(value) <= 0)){
                    LoggerTool.logger(str + " 数据异常，返回数字不小于等于对比数字  返回数据 ：" + r.getKey() + " = "+jsonObject.get(r.getKey())  + " 对比数据 ：" + r.getKey() + " = " + r.getValue());
                    return false;
                }
            }
        }
        return true;
    }
}
