
## JDK源码注释，基于1.8

access-bridge-64.jar    access桥接驱动
charsets.jar            Java 字符集，这个类库中包含 Java 所有支持字符集的字符

dnsns.jar               与 DNS 有关
jaccess.jar             辅助技术提供对实现Java Accessibility API的GUI工具包的访问（可见）
jce.jar                 Java 加密扩展类库，含有很多非对称加密算法在里面，但也是可扩展的
jfr.jar                 飞行记录器JFR

jsse.jar                Java 安全套接字扩展类库，用于实现加密的 Socket 连接
localedata.jar          本地机器语言的数据，比如日期在使用中文时，显示的是“星期四”之类的

resources.jar           资源包（图片、properties文件）
rt.jar                  运行时包

sunjce_provider.jar     为JCE 提供的加密安全套件
sunmscapi.jar           数字签名
sunpkcs11.jar           PKCS#11 证书工具

zipfs.jar               Zip File System Provider（文件压缩包）



## rt.jar（runtime）主要包路径
-com
    -oracle
    -sun
-java
    -applet     网页小程序
    -awt        图形界面设计
    -beans      配合反射，序列化等
    -io         普通IO，文件IO，流（重要）
    -lang       Java语言相关特性（重要）
    -math       数学软件包BigDecimal、BigInteger
    -net        网络包
    -nio        非阻塞IO（重要）
    -rmi        远程调用
    -security   
    -sql
    -text       文本转换SimpleDateFormat等
    -util       工具包，JUC（重要）
-javax
-org
-sun
