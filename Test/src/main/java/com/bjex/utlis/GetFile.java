package com.bjex.utlis;

import java.io.*;

public class GetFile {


    public static String readAllLineRetrnStr(File file){
        String list = new String();
        if (file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"GBK");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    list+=(line);
                }
                fileInputStream.close();
                inputStreamReader.close();
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                LoggerTool.logger("FileInputStream 类，创建失败 ， 配置文件获取失败,请检查配置文件格式是否正确");
                System.exit(1);
            } catch (UnsupportedEncodingException e) {
                LoggerTool.logger("InputStreamReader 类，创建失败 ， 配置文件获取失败，请检查配置文件格式是否正确");
                System.exit(1);
            } catch (IOException e) {
                LoggerTool.logger("读取文件失败 ， 配置文件获取失败，请检查配置文件格式是否正确");
                System.exit(1);
            }

        }
        return list;
    }
}
