package com.hczt.base.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tom on 17/7/18.
 */
@Service
public class QiniuManage {

    Properties properties;

    @PostConstruct
    void init() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("qiniu.properties");
        properties = new Properties();
        properties.load(inputStream);
    }


    public String upToken(String bucket, String key) {
        String accessKey = properties.getProperty("access.key");
        String secretKey = properties.getProperty("secret.key");
        Auth auth = Auth.create(accessKey, secretKey);
        return StringUtils.isNullOrEmpty(key) ? auth.uploadToken(bucket) : auth.uploadToken(bucket, key);
    }

}
