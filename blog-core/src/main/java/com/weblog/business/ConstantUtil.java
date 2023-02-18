package com.weblog.business;

public class ConstantUtil {

    public final static String AVATAR_URL_PATTERN = "/file/blogger/%d/avatar";
    public final static String AVATAR_REAL_PATH =
            System.getProperty("user.dir") + "/src/avatars/"; // 上传到用户当前目录下的指定目录中.

    public final static String ATTACHMENT_URL_PATTERN = "/file/blogger/%d/attachment/%d";
    public final static String ATTACHMENT_REAL_PATH =
            System.getProperty("user.dir") + "/src/files/";
}
