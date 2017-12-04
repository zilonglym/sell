package com.gittoy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * WechatAccountConfig
 * Create By GaoYu 2017/11/20 11:11
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /** 公众平台id */
    private String mpAppId;

    /** 公众平台秘钥 */
    private String mpAppSecret;

    /** 开放平台id */
    private String openAppId;

    /** 公众平台秘钥 */
    private String openAppSecret;

    /** 商户号 */
    private String mcId;

    /** 商户秘钥 */
    private String mchKey;

    /** 商户证书路径 */
    private String keyPath;

    /** 微信支付异步通知地址 */
    private String notifyUrl;

    /** 微信模版id */
    private Map<String, String> templateId;

}
