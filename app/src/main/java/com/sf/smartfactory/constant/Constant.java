package com.sf.smartfactory.constant;

import com.sf.smartfactory.BuildConfig;

import java.text.SimpleDateFormat;

/**
 * @author: winton
 * @time: 2018/3/30 17:21
 * @package: com.sf.smartfactory.constant
 * @project: SmartFactory
 * @mail:
 * @describe: 常量类
 */
public final class Constant {
    public static final String SP_TOKEN = "token";
    public static final String SP_USER_NAME = "name";
    public static final String SP_USER_GENDER = "gender";
    public static final String SP_USER_PHONE = "phone";
    public static final String SP_USER_USERNAME = "username";
    public static final String SP_USER_IMG = "user_img";
    public static final String SP_PASSWORD = "password";
    public static final String SP_IS_FIRST_OPEN = "first_open";
    public static final String SP_UPDATE_FREQUENCY_VALUE = "update_frequency_value";
    public static final String SP_UPDATE_FREQUENCY_TITLE = "update_frequency_title";
    public static final String SP_FACTORY_IMAGE = "factory_image";
    public static final String SP_FACTORY_DATE = "factory_date";
    public static final String SP_FACTORY_FOUND = "factory_found";
    public static final String SP_FACTORY_NAME = "factory_name";
    public static final String SP_FACTORY_EMP_NUM = "factory_emp_num";
    public static final String SP_FACTORY_LEGAL_PERSON = "factory_legal_person";




    public static final String TOKEN_ERROR = "token error";
    public static final String LOGIN_EXPIRATION = "登录失效";
    public static final String REQUEST_LIMIT = "请求频率限制";

    /*刷新间隔*/
    public static  int REFRESH_SPACE = 5 * 1000;

    /*统一的版本号*/
    public static final long VERSION_CODE = Integer.parseInt(BuildConfig.INNER_VERSION);

    public static final String APK_DOWNLOAD_NAME = "release.apk";

    public static final SimpleDateFormat XAXIS_SDF = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat SERVER_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat USE_TIME_SDF = new SimpleDateFormat("HH:mm:ss");
}
