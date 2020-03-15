package com.nanrailgun.springbootoss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;

/**
 * 七牛云oss配置类
 */
public class QiNiuConfig {

    //公钥
    public static final String accessKey="niBP4srpbhIhMZ8LOci0qxBfeFQneSLG__IDwfy-";
    //私钥
    public static final String secretKey="8nC1KwJ-UBY7KAdp3C09QaI0FZC4zLKB7g_gENRB";
    //空间名称
    public static final String bucket = "nanrailguntest1";

    private static UploadManager uploadManager;


    static {
        Configuration cfg = new Configuration(Zone.autoZone());
        uploadManager = new UploadManager(cfg);
    }

    public static UploadManager getUploadManager(){
        return uploadManager;
    }
}
