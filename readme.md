## 微信网页授权：

- 1）官方文档：
https://mp.weixin.qq.com/wiki

- 2）调试：
https://natapp.cn

- 3）第三方SDK
https://github.com/Wechat-Group/weixin-java-tools

- 4）微信支付
https://pay.weixin.qq.com/wiki/doc/api/index.html

- 5）手机代理服务器
windows:fiddler
mac:Charles
用手机代理将手机请求到本地电脑上，用来调试手机微信访问。

6）微信支付第三方SDK
https://github.com/Pay-Group/best-pay-sdk

## java -jar方式启动程序（建议用该种方式启动，比部署Tomcat方式要好）

- 1）-Dserver.port：指定端口；
- 2）-Dspring.profiles.active：指定使用哪一个配置文件

java -jar -Dserver.port=8090 -Dspring.profiles.active=prod sell.jar