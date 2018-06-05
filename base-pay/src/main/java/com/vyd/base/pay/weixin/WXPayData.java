package com.vyd.base.pay.weixin;

import org.apache.commons.beanutils.BeanMap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信统一下单数据
 * 详情查看：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
 */
public class WXPayData {

    /**
     * 支付类型
     **/
    public static enum TradeType {
        JSAPI,          //公众号支付
        NATIVE,         //原生扫码支付
        APP;            //app支付
    }

    /**
     * 必填字段
     **/
    public String body;                    //商品描述--商品简单描述，该字段请按照规范传递，具体请见
    public String outTradeNo;            //商户订单号--商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
    public Long   totalFee;               //标价金额--订单总金额，单位为分
    public String notifyUrl;              //通知地址--异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
    public String tradeType;              //交易类型--取值如下：JSAPI，NATIVE，APP等
    public String productId;              //商品ID--trade_type=NATIVE时（即扫码支付），此参数必传。
    public String openid;                  //用户标识--trade_type=JSAPI时（即公众号支付），此参数必传;
    public String spbillCreateIp;        //终端IP

    /**
     * 非必填字段
     **/
    public String deviceInfo = "WEB";     //设备号--自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
    public String detail;                  //商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
    public String attach;                  //附加数据--附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
    public String feeType = "CNY";        //标价币种
    public String timeStart;              //交易起始时间
    public String timeExpire;             //交易结束时间
    public String goodsTag;               //订单优惠标记
    public String limitPay;               //指定支付方式
    public String sceneInfo;              //场景信息

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public Map<String, String> toMapData() {
        Map<String, String> mapData = new HashMap<>();
        Map<Object, Object> beanMap = new BeanMap(this);
        for (Map.Entry<Object, Object> entry : beanMap.entrySet()) { // It's not parameterized :(
            if (entry.getValue() != null) {
                String key = toUnderscoreName(entry.getKey().toString());
                mapData.put(key, entry.getValue().toString());
            }
        }
        mapData.remove("class");
        return mapData;
    }

    private String toUnderscoreName(String camelcase) {
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(camelcase);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "_" + m.group().toLowerCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }


    public static void main(String[] args) {
        WXPayData wxPayData = new WXPayData();
        wxPayData.setBody("会员充值");
        wxPayData.setOutTradeNo("123");
        Map<String, String> map = wxPayData.toMapData();
        System.out.printf(map.toString());
    }
}
