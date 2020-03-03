### 一、项目结构

> wmh-shop-parent
>
> -----wmh-shop-basics----分布式基础设施
>
> ---------wmh-shop-basics-alibaba-nacos—注册中心 8080
>
> ---------wmh-shop-basics-alibaba-nacos—分布式配置中心 8080  
>
> ---------wmh-shop-basics-alibaba-seata 分布式事务解决方案 8730
>
> ---------wmh-shop-basics-alibaba-canal mysql与redis一致性的问题
>
> ---------wmh-shop-basics-springcloud-gateway  统一请求入口 80
>
> ---------wmh-shop-basics-xuxueli-xxljob  分布式任务调度平台
>
> ---------wmh-shop-basics-codingapi-zipKin  分布式调用链系统
>
> 
>
> -----wmh-shop-service-api  提供公共接口
>
> ------------ wmh-shop-service-api-weixin  微信服务接口
>
> ------------ wmh-shop-service-api-member  会员服务接口
>
> ------------ wmh-shop-service-api-sso  sso服务接口
>
> ------------ wmh-shop-service-api-item  商品服务接口
>
> ------------ wmh-shop-service-api-search 搜索服务接口
>
> ------------ wmh-shop-service-api-pay  聚合支付平台
>
> ------------ wmh-shop-service-api-order  订单服务接口
>
> ------------ wmh-shop-service-api-spike  秒杀服务接口
>
> ------------ wmh-shop-service-api-sms  消息服务平台
>
>  
>
> 服务接口中包含内存内容: 实体类层、接口层 
>
>  
>
> -----wmh-shop-service-impl公共接口的实现
>
> ------------ wmh-shop-service-weixin 微信服务接口实现 9090
>
> ------------ wmh-shop-service-member  会员服务接口实现 7070
>
> ------------ wmh-shop-service-api-sso sso服务接口实现 6060 
>
> ------------ wmh-shop-service-tem  商品服务接口实现 5050
>
> ------------ wmh-shop-service-search 搜索服务接口实现 3030 
>
> ------------ wmh-shop-service-pay  聚合支付平台接口实现 2020 
>
> ------------ wmh-shop-service-order  订单服务接口实现 1010 
>
> ------------ wmh-shop-service-spike 秒杀服务接口 4040
>
> ------------ wmh-shop-service-sms 消息服务平台 9810
>
>  
>
> -----wmh-shop-common 工具类
>
> ---------wmh-shop-common-core—核心工具类
>
>  
>
> ----wmh-shop-job---任务调度工程
>
> ----wmh-shop-pay- reconcile --- 支付对账任务
>
> ----wmh-shop-pay- sms --- 消息服务平台
>
>  
>
> -----wmh-shop-portal 门户平台 vue工程
>
> --------wmh-shop-portal-web 门户网站  支持PC端和H5端访问
>
> --------wmh-shop-portal-sso 单点登陆系统 
>
> --------wmh-shop-portal-search 搜索系统
>
> --------wmh-shop-portal-spike 秒杀系统
>
> --------wmh-shop-portal-cms 系统 