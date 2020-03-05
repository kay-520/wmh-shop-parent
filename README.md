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



### 二、中间件地址信息

#### 1.Nacos

```
http://localhost:8848/nacos     nacos/nacos
```

#### 2. Gitlab

```
http://192.168.75.128:8090/     admin/wmh19980515
```

#### 3.Nexus

```
http://192.168.75.128:8081/    admin/admin,wmh/wmh
```



### 三、中间件环境搭建

#### 1.Gitlab环境搭建

##### 1.1 下载镜像文件

```shell
docker pull beginor/gitlab-ce:11.0.1-ce.0
```

##### 1.2 创建Gitlab的配置（etc），日志（log），数据（data）放到容器之外，便于日后升级。

```shell
mkdir -p /mnt/gitlab/etc
mkdir -p /mnt/gitlab/log
mkdir -p /mnt/gitlab/data
```

##### 1.3 运行Gitlab容器

```shell
docker run \
    --detach \
    --publish 8443:443 \
    --publish 8090:80 \
    --name gitlab \
    --restart unless-stopped \
    -v /mnt/gitlab/etc:/etc/gitlab \
    -v /mnt/gitlab/log:/var/log/gitlab \
    -v /mnt/gitlab/data:/var/opt/gitlab \
    beginor/gitlab-ce:11.0.1-ce.0 
```

##### 1.4 修改/mnt/gitlab/etc/gitlab.rb

把external_url改成部署机器的域名或者IP地址

```shell
vi /mnt/gitlab/etc/gitlab.rb
external_url 'http://192.168.75.128'
```

##### 1.5 修改/mnt/gitlab/data/gitlab-rails/etc/gitlab.yml

```shell
vi /mnt/gitlab/data/gitlab-rails/etc/gitlab.yml
```

找到关键字## Web server settings

将host的值改成映射的外部主机ip地址和端口，这里会显示在gitlab克隆地址

到此，gitlab可以正常访问。

##### 1.6 创建用户，创建项目



#### 2.Nexus环境搭建

##### 2.2 下载镜像

```shell
#1.下载一个nexus3的镜像
docker pull sonatype/nexus3
```

##### 2.2 运行Nexus容器

```shell
#2.将容器内部/var/nexus-data挂载到主机/root/nexus-data目录
docker run -d -p 8081:8081 --name nexus -v /root/nexus-data:/var/nexus-data --restart=always sonatype/nexus3
```

关闭防火墙，访问http://ip:8081

进入容器获取初始密码：

```shell
docker exec -it nexus /bash/bin
vi /nexus-data/admin.password
```

默认登录账号：admin/获取的密码，并修改密码

##### 2.3  创建私服账户和仓库

创建账户（wmh/wmh），并赋予权限

创建maven2(hosted)，私人仓库：wmh-release

修改maven-central仓库中心仓库地址为阿里云仓库

```
http://maven.aliyun.com/nexus/content/groups/public/
```

##### 2.4 修改本地maven，settings.xml文件

```xml
<mirrors>
    <mirror>
		<id>wmh</id>
		<name>Repository in My Orgnization</name>
		<url>http://192.168.75.128:8081/repository/maven-public/</url>
		<mirrorOf>*</mirrorOf>
	</mirror>
  </mirrors>
 

  <servers>
		<server>    
       <id>wmh</id>    
       <username>wmh</username>    
       <password>wmh</password>    
    </server>
  </servers>
  
   <profiles>

         <repository> 
		  <id>wmh</id> 
		  <url>http://192.168.75.128:8081/repository/maven-snapshots/</url> 
		  <releases><enabled>true</enabled><updatePolicy>always</updatePolicy></releases> 
		  <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots> 
		</repository> 

  </profiles>
```

##### 2.5 打包本地jar包到私服

添加pom依赖：

```xml
<!--注意限定版本一定为RELEASE,因为上传的对应仓库的存储类型为RELEASE -->
	<!--指定仓库地址 -->
	<distributionManagement>
		<repository>
			<!--此名称要和.m2/settings.xml中设置的ID一致 -->
			<id>wmh</id>
			<url>http://192.168.75.128:8081/repository/wmh-release/</url>
		</repository>
	</distributionManagement>

	<build>
		<plugins>
			<!--发布代码Jar插件 -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-deploy-plugin</artifactId>
				<version>2.7</version>
			</plugin>
			<!--发布源码插件 -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<version>2.2.1</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>jar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

执行命令：

```shell
mvn deploy
```

### 其他

#### 1.Swagger配置说明 doc.html

> @Api：用在请求的类上，表示对类的说明
>
>   	tags="说明该类的作用，可以在UI界面上看到的注解"
>
>  	 value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
>
>  
>
> @ApiOperation：用在请求的方法上，说明方法的用途、作用
>
>  	 value="说明方法的用途、作用"
>
>  	 notes="方法的备注说明"
>
>   
>
> @ApiImplicitParams：用在请求的方法上，表示一组参数说明
>
>   @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
>
>    	 name：参数名
>
>    	 value：参数的汉字说明、解释
>
>    	 required：参数是否必须传
>
> ​    	paramType：参数放在哪个地方
>
> ​     	 · header --> 请求参数的获取：@RequestHeader
>
> ​     	 · query --> 请求参数的获取：@RequestParam
>
>    	   · path（用于restful接口）--> 请求参数的获取：@PathVariable
>
> ​    	  · body（不常用）
>
>    	   · form（不常用）  
>
>   	  dataType：参数类型，默认String，其它值dataType="Integer"    
>
>   	  defaultValue：参数的默认值
>
>  
>
> @ApiResponses：用在请求的方法上，表示一组响应
>
>   @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
>
>   	  code：数字，例如400
>
>  	   message：信息，例如"请求参数没填好"
>
>   	  response：抛出异常的类
>
>  
>
> @ApiModel：用于响应类上，表示一个返回响应数据的信息
>
> ​      （这种一般用在post创建的时候，使用@RequestBody这样的场景，
>
> ​      请求参数无法使用@ApiImplicitParam注解进行描述的时候）
>
>   @ApiModelProperty：用在属性上，描述响应类的属性







 



 









