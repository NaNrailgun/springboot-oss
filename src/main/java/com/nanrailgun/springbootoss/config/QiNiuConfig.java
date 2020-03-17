package com.nanrailgun.springbootoss.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

/**
 * 七牛云oss配置类
 */
@Component
public class QiNiuConfig {

    //公钥
    private final String accessKey="niBP4srpbhIhMZ8LOci0qxBfeFQneSLG__IDwfy-";
    //私钥
    private final String secretKey="8nC1KwJ-UBY7KAdp3C09QaI0FZC4zLKB7g_gENRB";
    //七牛域名
    public final String url="http://q76ujmt72.bkt.clouddn.com/";
    //空间名称
    public final String bucket = "nanrailguntest1";
    //配置区域
    public final Configuration cfg = new Configuration(Zone.autoZone());

    public final Auth auth = Auth.create(accessKey, secretKey);
}
