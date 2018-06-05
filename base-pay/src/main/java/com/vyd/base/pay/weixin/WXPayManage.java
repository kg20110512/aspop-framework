package com.vyd.base.pay.weixin;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.codec.binary.StringUtils;

import java.util.Base64;
import java.util.Map;

public class WXPayManage {

    private WXPay           wxPay;
    private WXPayConfigImpl wxPayConfig;

    public WXPayManage() throws Exception {
        wxPayConfig = WXPayConfigImpl.getInstance();
        wxPay = new WXPay(wxPayConfig);
    }


    public Map<String, String> doUnifiedOrder(WXPayData wxPayData) throws Exception {
        if (wxPayData.getNotifyUrl() == null || wxPayData.getNotifyUrl().equals("")) {
            wxPayData.setNotifyUrl(wxPayConfig.getNotifyUrl());
        }
        return wxPay.unifiedOrder(wxPayData.toMapData());
    }

    public static void main(String[] args) throws Exception {
        WXPayData payData = new WXPayData();
        payData.setBody("会员充值");
        payData.setOutTradeNo("201613091059590000003433-asd005");
        payData.setTotalFee(1L);
        payData.setSpbillCreateIp("192.168.31.243");
        payData.setNotifyUrl("http://test.letiantian.me/wxpay/notify");
        payData.setTradeType(WXPayData.TradeType.NATIVE.name());
        payData.setProductId("11");
        Map<String, String> result = new WXPayManage().doUnifiedOrder(payData);
        System.out.printf(result.toString());
    }
}
