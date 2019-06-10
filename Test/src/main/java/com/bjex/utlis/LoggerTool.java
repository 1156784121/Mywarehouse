package com.bjex.utlis;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class LoggerTool {

    public static void logger(String log){
        String path = System.getProperty("user.dir");
        String logPath = path + "\\result.log";
        Logger logger = Logger.getLogger(logPath);
        logger.setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler(logPath,true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new Formatter(){
                @Override
                public String format(LogRecord record) {
                    Date date= new Date();
                    String sDate = date.toString();
                    return "[" + sDate + "]" + "[" +record.getLevel() + "]   " + record.getMessage() + "\n";
                }
            });
            logger.addHandler(fileHandler);
            logger.info(log);
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
