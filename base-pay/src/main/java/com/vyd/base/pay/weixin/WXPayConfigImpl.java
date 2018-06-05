package com.vyd.base.pay.weixin;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class WXPayConfigImpl implements WXPayConfig {

    private static WXPayConfigImpl INSTANCE;

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    Properties properties;

    public WXPayConfigImpl() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pay.properties");
        properties = new Properties();
        properties.load(inputStream);
    }

    @Override
    public String getAppID() {
        return properties.getProperty("pay.weixin.appid");
    }

    @Override
    public String getMchID() {
        return properties.getProperty("pay.weixin.machid");
    }

    @Override
    public String getKey() {
        return properties.getProperty("pay.weixin.key");
    }

    public String getNotifyUrl(){
        return properties.getProperty("pay.weixin.notify_url");
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
