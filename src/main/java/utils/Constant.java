package utils;

import java.io.*;
import java.util.Properties;

import static utils.LLogger.logger;

public class Constant {
    public final static String HOME = System.getProperty("user.dir");
    public static String beforeFilePath = "";
    public static String transformedFilePath = "";
    public static boolean renameMethod = false;
    public static boolean renameVar = true;
    public static boolean transLoop = true;
    public static boolean switchToIf = true;
    public static boolean insertLog = true;
    public static boolean reorderCondition = true;
    public static boolean negateCondition = true;
    public static String fakeClassName = "FAKECLASS";
    public static String configFilePath = "/Users/ffengjay/Postgraduate/DataLeakage/src/dependencies/setting.properties";
    public static String jsonFilePath = "";
//
    static {
        Properties prop = new Properties();
        try{
            InputStream inputStream = new BufferedInputStream(new FileInputStream(HOME + "/src/main/resources/setting.properties"));
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(Constant.configFilePath));
            prop.load(inputStream);
            renameMethod = prop.getProperty("renameMethod", "false").equals("true");
            renameVar = prop.getProperty("renameVar", "true").equals("true");
            transLoop = prop.getProperty("transLoop", "true").equals("true");
            switchToIf = prop.getProperty("switchToIf", "true").equals("true");
            insertLog = prop.getProperty("insertLog", "true").equals("true");
            reorderCondition = prop.getProperty("reorderCondition", "true").equals("true");
            negateCondition = prop.getProperty("negateCondition", "true").equals("true");
            fakeClassName = prop.getProperty("fakeClassName", "FAKECLASS");
        } catch (IOException e) {
            logger.error("failed to load configure file...");
            throw new RuntimeException(e);
        }
    }
}