package com.vyd.base.pay.ali;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

public class AliPay {
	/**
	 * 日志记录
	 */
	private static final Logger log = LoggerFactory.getLogger(AliPay.class);
	
	public static final String KEY_SERVER_URL = "https://openapi.alipay.com/gateway.do";
	public static final String KEY_APP_ID = "2088911702643917";
	public static final String KEY_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOnm8vqkMY6ZutHg1X8IMiSU10ctNGfZUL6yrFiI+W2O+DkFGZ8LR0CP0MuSx6nHeFaaVOI8d7HFDPuYmbaeNKIQ/rsRZtZGYxepB15qHwq3zyvnZY5VdKcTfa8/AS/0IhVXJZFNEfo1XHnyIjsugEwD4XK/HkHUmUsemCFQXbbTAgMBAAECgYEAvFjpjeTLtx2Zb0nSAUHzRrvIApunGHzI5VZxK7XYzF0VCq1NrWFild1474SZXOCvyWTrN2YjqDWZt7txA7mvfMt2VjwvLYaX/Q3Ew+t9GTH/weHBZVzfbT40cKsYOIlxiW/zAmxYpDQfsc+/vQ/ffWE2o/1EDaqauytwJ6cLgfkCQQD44ME1vhOpO2FxrUEZavBm6424SVqJXmfHMZYOYEnnqDCvZiPxw1K7FT9HzwjG7bq7mErVJ2YujMxW4IO/WYtXAkEA8Jh714xnIq9mXF2iCpF0MbZTNHvS8QhqpeammQ0Jw18qObdLjuqV+L5PnBH3hUW2Yk0oL5+66n05Gts7GIM+5QJBAPK+tXTnymF8C5VG+YIDLiwUgIxI78egjvRYxxWKkUskXygVJeX/L0hOdwRhSRxOb53d8s9ouF+a8QujvJz8h80CQHL67HRejQtud6S8uqwCR11anPkAFBdMxmryh7utF3VfDXb3ugxa38E4ciNMEtaGMltYNFROKa3WmmvTSbK1RmECQBdQzZ3miJUZapT5iCzDb3ArCfQdKBz7zDysiN/iFrgMuDQgsEwvOLxfjjz21YoYaBP7thfZfBf4BfJ1afhZn2A=";
	
	public static void main(String[] args) throws AlipayApiException {
		AlipayClient aliPayClient = new DefaultAlipayClient(KEY_SERVER_URL, KEY_APP_ID, KEY_PRIVATE_KEY);
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo("22222");
		model.setTotalAmount("0.01");
		model.setProductCode("AABBB");
		request.setBizModel(model);
		request.setNotifyUrl("http://www.baidu.com");
		AlipayTradeAppPayResponse response = aliPayClient.sdkExecute(request);
		
		log.info("支付宝支付返回客户端数据：{}",response.getBody());
	}
}