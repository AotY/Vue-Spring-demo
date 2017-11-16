# Vue-Spring-demo



## 项目展示

- 首页

![](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvdiq2wj31kw0zkn86.jpg)

- 列表

![](https://ws4.sinaimg.cn/large/006tNc79ly1fjyad61120j31kw0zkn67.jpg)

- 详情

![](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvj0yt3j31kw0zkagw.jpg)

- 购物车

![](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvmrwnpj31kw0zk10k.jpg)



- 订单确认

  ![confirm](https://ws2.sinaimg.cn/large/006tKfTcly1fk60bjddqdj31kw0zkwj0.jpg)

- 订单支付

  ![payment](https://ws4.sinaimg.cn/large/006tKfTcly1fk60bi142zj31kw0zkgst.jpg)

- 订单详情

![order-detail](https://ws4.sinaimg.cn/large/006tKfTcly1fk60bm8fuhj31kw0zkn3j.jpg)



## 技术栈

### 1. 后端

- spring-mvc
- MySQL

### 2. 前端

- Vue.js
- Vue Router
- Vuex
- axios

## 1. 环境配置

### 1.1 安装CentOS 

> 安装时用户名：xjtu
>
> 密码：xjtuxjtu
>
> 用户名和密码请保持一致。

1. 下载CentOS镜像

   http://mirrors.aliyun.com/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-1708.iso

2. 在虚拟机中安装CentOS。

3. 添加xjtu用户到sudoers文件 （添加xjtu用户到sudo用户组）

   ```
   1. 切换到root用户
   su
   2. 编辑/etc/sudoers文件
   vi /etc/sudoers
   3. 在93行添加一下内容(set nu显示行号)
   xjtu	ALL=(ALL)	ALL
   4. 保存退出
   5. 退出root用户
   exit
   ```

   > 参考： https://jingyan.baidu.com/article/9f63fb91a9a7a3c8400f0ecb.html

4. 安装成功后配置阿里云Linux安装软件镜像源（为了提高安装软件速度）

   ```
   1. 备份你的原镜像文件
   sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bac
   2. 下载新的CentOS-Base.repo文件并保存到/etc/yum.repos.d/下
   sudo wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
   3. 运行yum makecache生成缓存
   sudo yum makecache
   ```
   > 命令中的-O 是大写的O，不是数字0。

5. 安装SSH（远程登录）和wget

   ```
   sudo yum -y install openssh-server

   sudo yum install -y wget
   ```

6. 配置网络（确保能上网）

   ​
### 1.2 git配置

1. 下载安装

   ```
   sudo yum -y install git 
   ```

2. 下载项目代码

   ```
   sudo mkdir /developer

   sudo chown -R xjtu.xjtu /developer

   mkdir /developer/git-repository
     
   cd /developer/git-repository

   git clone https://github.com/AotY/Vue-Spring-demo
   ```




### 1.3 selinux配置

```
sudo cp /developer/git-repository/Vue-Spring-demo/config/selinux/config /etc/sysconfig/selinux

su

setenforce 0

exit
```



### 1.4 iptables配置

1. 关闭防火墙

   ```
   sudo systemctl stop firewalld 
   sudo systemctl mask firewalld
   ```

2. 安装iptables

   ```
   sudo yum install -y iptables-services
   sudo systemctl enable iptables
   ```

3. 配置iptables

   ```
   sudo cp /developer/git-repository/Vue-Spring-demo/config/iptables /etc/sysconfig/iptables
   ```

4. 启动iptables

   ```
   sudo service iptables save
   sudo systemctl start iptables
   ```




### 1.5 hosts配置

```
sudo cp /developer/git-repository/Vue-Spring-demo/config/hosts /etc/hosts
```



### 1.6 Java配置

1. 下载

   ```
   cd ~

   wget http://learning.happymmall.com/jdk/jdk-7u80-linux-x64.rpm
   ```

2. 安装 (安装默认位置在/usr/java/)

   ```
   sudo rpm -Uvh jdk-7u80-linux-x64.rpm
   ```

3. 配置profile

   ```
   sudo cp /developer/git-repository/Vue-Spring-demo/config/profile /etc/profile
   ```




### 1.7 Tomcat配置

1. 下载安装

   ```
   cd ~

   # wget http://mirror.bit.edu.cn/apache/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.tar.gz (这个链接失效了，请使用下面的一条命令(地址))

   wget https://oiw0skz2u.qnssl.com/o_1bv1pv3nh1aqv1b8q1p5s14411fu1h.gz\?attname\=apache-tomcat-8.5.20.tar.gz\&e\=1510819228\&token\=RhbJn4QNVTV6pnuObXel6iSMlhnWHKR9b_tp6Xeu:pbl1dUyUApZhRO8Xny1W04RhHlI\= -O apache-tomcat-8.5.20.tar.gz 

   tar -xvf apache-tomcat-8.5.20.tar.gz

   mv apache-tomcat-8.5.20 /developer
   ```

   ​

2. 配置

   ```
   cp /developer/git-repository/Vue-Spring-demo/config/tomcat/server.xml /developer/apache-tomcat-8.5.20/conf/server.xml

   cp /developer/git-repository/Vue-Spring-demo/config/tomcat/setenv.sh /developer/apache-tomcat-8.5.20/bin/setenv.sh
   ```

   ​

3. 启动

   ```
   /developer/apache-tomcat-8.5.20/bin/startup.sh
   ```



### 1.8 Maven配置

1. 安装

   ```
   wget http://learning.happymmall.com/maven/apache-maven-3.0.5-bin.tar.gz

   tar -xvf apache-maven-3.0.5-bin.tar.gz

   mv apache-maven-3.0.5 /developer
   ```

2. 配置

   ```
   cp /developer/git-repository/Vue-Spring-demo/config/maven/settings.xml /developer/apache-maven-3.0.5/conf/settings.xml
   ```



### 1. 9 vsftpd配置

1. 安装vsftpd

   ```
   sudo yum install -y vsftpd
   ```

   ​

2. 配置vsftpd

   ```
   1. 创建ftp用户(只能登录ftp，不能登录系统)
   sudo mkdir /ftpfile # ftp文件夹

   sudo useradd ftpuser -d /ftpfile -s /sbin/nologin  #创建用户，没有登陆系统权限

   sudo passwd ftpuser  #设置ftpuser密码(这里设置为12345678)

   sudo chown -R ftpuser.ftpuser /ftpfile  #赋予ftpuser访问ftpfile文件夹权限

   2. 配置vsftpd.conf
   sudo cp /developer/git-repository/Vue-Spring-demo/config/vsftp/vsftpd.conf /etc/vsftpd/vsftpd.conf

   sudo cp /developer/git-repository/Vue-Spring-demo/config/vsftp/chroot_list /etc/vsftpd/chroot_list
   ```

3. 启动vsftpd

  ```
  sudo service vsftpd start

  sudo chkconfig vsftpd on # 设置开机自启

  vsftpd相关命令：
  sudo service vsftpd start
  sudo service vsftpd stop
  sudo service vsftpd restart
  ```

4. 导入项目需要使用的图片

   ```
   sudo yum install -y unzip

   sudo cp /developer/git-repository/Vue-Spring-demo/ftpfile/image.zip /ftpfile/

   cd /ftpfile

   sudo unzip image.zip

   sudo chown -R ftpuser.ftpuser /ftpfile #(因为image.zip拥有者是root)
   ```

   ​

### 1.10 nginx配置 

1. 下载安装

   ```
   cd ~

   wget http://nginx.org/download/nginx-1.12.1.tar.gz

   tar -xvf nginx-1.12.1.tar.gz

   cd nginx-1.12.1

   sudo yum install -y gcc gcc-c++ make zlib-devel pcre-devel openssl-devel

   ./configure --prefix=/usr/local/nginx

   make

   sudo make install
   ```

2. 配置

   ```
   sudo cp /developer/git-repository/Vue-Spring-demo/config/nginx/nginx.conf /usr/local/nginx/conf

   sudo mkdir /usr/local/nginx/conf/vhost

   sudo cp /developer/git-repository/Vue-Spring-demo/config/nginx/vhost/www.vuespringdemo.com.conf /usr/local/nginx/conf/vhost/

   sudo cp /developer/git-repository/Vue-Spring-demo/config/nginx/vhost/backend.vuespringdemo.com.conf /usr/local/nginx/conf/vhost/

   sudo cp /developer/git-repository/Vue-Spring-demo/config/nginx/vhost/image.vuespringdemo.com.conf /usr/local/nginx/conf/vhost/

   ```


   

3. 启动nginx

   ```
   cd /usr/local/nginx/sbin

   sudo ./nginx
   ```

   ​

### 1.11 MySQL配置

1. 安装

   ```
   1. 查看系统是否安装了MySQL
   rpm -qa | grep mysql
   2. 如果安装了使用下面命令卸载
   sudo rpm -qa | grep mysql
   yum remove mysql mysql-client mysql-server mysql-common mysql-devel
   sudo mv /var/lib/mysql /var/lib/mysql_old_backup
   sudo rm -rf /var/lib/mysql/
   sudo rm -rf /etc/my.cnf
   ```

   ​


2. 安装

   ```
   wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm

   sudo rpm -ivh mysql-community-release-el7-5.noarch.rpm

   sudo yum update

   sudo yum install mysql-server
   ```

3. 启动

   ```
   sudo systemctl enable mysqld

   sudo systemctl start mysqld
   ```

4. 登录（第一次登录不用密码）

   ```
   mysql -uroot -p
   ```

5. 修改root密码

   ```
   1. 查看用户
   select user, host, password from mysql.user;
   2. 设置密码
   set password for root@localhost=password('xjtuxjtu');
   set password for root@xjtu=password('xjtuxjtu');
   set password for root@127.0.0.1=password('xjtuxjtu');
   ```

   ​

6. 删除匿名用户

   ```
   delete from mysql.user where user='';

   刷新权限
   flush privileges; 
   ```

7. 新增用户 

   ```
   insert into mysql.user(host, user, password, ssl_cipher, x509_issuer, x509_subject) values ("localhost", "xjtu", password("xjtuxjtu"), "", "", "");

   flush privileges; 
   ```

8. 创建数据库

   ```
   CREATE DATABASE vue_spring_demo DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

   use vue_spring_demo;
   ```

9. 赋予用户权限

   ```
   1. 本地权限
   grant all privileges on vue_spring_demo.* to xjtu@localhost identified by 'xjtuxjtu';
   2. 远程权限
   grant all privileges on vue_spring_demo.* to xjtu@'%' identified by 'xjtuxjtu';
   3.刷新权限
   flush privileges; 
   ```

10. 导入数据库

  ```
  exit;

  mysql -u xjtu -p vue_spring_demo < /developer/git-repository/Vue-Spring-demo/backend/vue_spring_demo.sql

  ```

> `::1` is the IPv6 address for localhost.

   

### 1.12 Node js配置

1. 下载安装nodejs

   ```
   cd ~

   wget https://nodejs.org/dist/v6.11.3/node-v6.11.3-linux-x64.tar.xz

   sudo mkdir /usr/local/nodejs

   sudo tar -xJvf node-v6.11.3-linux-x64.tar.xz -C /usr/local/nodejs

   sudo mv /usr/local/nodejs/node-v6.11.3-linux-x64/ /usr/local/nodejs/node-v6.11.3
   ```

   ​

2. 使环境变量（之前已经配置）  

   ```
   source /etc/profile
   ```

3. 验证

   ```
   node -v

   npm version
   ```

4. 安装cnpm（为了提高安装依赖包的速度）  

   ```
    npm install -g cnpm --registry=https://registry.npm.taobao.org
   ```

   ​

 ### 1.13 本机host配置 

在本机（不是虚拟机）hosts文件中添加以下几行记录。

> 注意的是，需要将前面的IP换为自己虚拟机中的centOS的IP。

```
192.168.246.204(替换为自己的虚拟机ip) image.vuespringdemo.com

192.168.246.204(替换为自己的虚拟机ip) backend.vuespringdemo.com

192.168.246.204(替换为自己的虚拟机ip) www.vuespringdemo.com

```



## 2. 编译部署

1. 进入项目目录

  ```
  cd /developer/git-repository/Vue-Spring-demo
  ```
2. 编译部署

  ```
  sh ./deploy.sh
  ```
3. 访问测试 

   在本机打开浏览器，地址栏输入：

   ```
   www.vuespringdemo.com
   ```


如果能够看到页面主页则说明配置成功。



## 3. 接口说明

### 3.1 user（用户相关）

#### 3.1.1 登录

- 接口

  ```   ​  
  http://backend.vuespringdemo.com/user/login.do
  ```
- 请求方式

  ```
  POST
  ```
- 参数（例子）

  ```
  username=admin
  password=1234
  ```
- 返回值

  ```
  {
  "status": 0,
  "data":{
  "id": 26,
  "username": "admin",
  "password": "",
  "email": "qingtaogg@gmail.com",
  "phone": "13800138000",
  "question": "问题",
  "answer": "答案",
  "role": 1,
  "createTime": null,
  "updateTime": null
  }
  }
  ```


#### 3.1.2 注销

- 接口
  ```
  http://backend.vuespringdemo.com/user/logout.do
  ```
- 请求方式

  ```
  POST
  ```
- 参数

  ```
  无
  ```
- 返回值

  ```
  {
  "status": 0,
  "msg": "SUCCESS"
  }
  ```
#### 3.1.3 注册

- 接口

  ```
  http://backend.vuespringdemo.com/user/register.do
  ```
- 请求方式

  ```
  POST
  ```
- 参数（例子）

  ```
  username=test
  password=1234
  email=test@test.com
  ```
- 返回值

  ```
  {
  "status": 0,
  "msg": "注册成功"
  }
  ```

#### 3.1.4 获取用户信息

- 接口

  ```
  http://backend.vuespringdemo.com/user/get_user_info.do
  ```
- 请求方式

  ```
  GET
  ```
- 参数

  ```
  无
  ```
- 返回值

  ```
  {
  "status": 0,
  "data":{
  "id": 26,
  "username": "admin",
  "password": "",
  "email": "qingtaogg@gmail.com",
  "phone": "13800138000",
  "question": "问题",
  "answer": "答案",
  "role": 1,
  "createTime": null,
  "updateTime": null
  }
  }
  ```



### 3.2 推荐

#### 3.2.1 滚动图片

- 接口  

  ```
  http://backend.vuespringdemo.com/recommend/carousel.do
  ```
- 请求方式

  ```
  GET
  ```
- 参数

  ```
  无
  ```
- 返回值

  ```
  {
  "status": 0,
  "data":{
  "imageHost": "http://image.vuespringdemo.com/",
  "carousels":[
    {"id": 32, "productId": 27, "name": "Apple iPhone 8 (A1863) 64GB 深空灰色 移动联通电信4G手机", "image": "carousel-iPhone8.jpg",…},
    {"id": 33, "productId": 28, "name": "锤子 坚果Pro 128GB 细红线特别版 全网通 移动联通电信4G手机 双卡双待", "image": "carousel-smartian-u2.jpg",…}
    ]
  }
  }
  ```


#### 3.2.1 推荐搜索关键词

- 接口

  ```
  http://backend.vuespringdemo.com/recommend/keyword.do
  ```
- 请求方式

  ```
  GET
  ```
- 参数

  ```
  无
  ```
- 返回值

  ```
  {
  "status": 0,
  "data":[
  "手机",
  "锤子"
  ]
  }
  ```



### 3.3 product (商品相关)

#### 3.3.1 商品列表

- 接口

  ```
  http://backend.vuespringdemo.com/product/list.do?keyword=手机&categoryId=0&orderBy=price_desc&pageSize=10&pageNum=1
  ```
- 请求方式

  ```
  GET
  ```
- 参数

  ```
  keyword=手机 (可选)
  categoryId=0(可选)
  orderBy=price_desc(可选)
  pageSize=10(可选)
  pageNum=1 (可选)
  ```

- 返回值

  ```
  {
  "status": 0,
  "data":{
  "pageNum": 1,
  "pageSize": 1,
  "size": 1,
  "orderBy": "price desc",
  "startRow": 1,
  "endRow": 1,
  "total": 3,
  "pages": 3,
  "list":[
    {"id": 27, "categoryId": 100012, "name": "Apple iPhone 8 Plus (A1864) 256GB 金色 移动联通电信4G手机", "subtitle": "【iPhone新品上市】新一代iPhone，让智能看起来更不一样",…}
    ],
  "firstPage": 1,
  "prePage": 0,
  "nextPage": 2,
  "lastPage": 3,
  "isFirstPage": true,
  "isLastPage": false,
  "hasPreviousPage": false,
  "hasNextPage": true,
  "navigatePages": 8,
  "navigatepageNums":[
  1,
  2,
  3
  ]
   }
  }
  ```


#### 3.3.2 商品详情

- 接口

  ```
  http://backend.vuespringdemo.com/product/detail.do?productId=26
  ```
- 请求方式

  ```
  GET
  ```
- 参数  

  ```
  productId=26
  ```
- 返回值

  ```
  {
  "status": 0,
  "data":{
  "id": 26,
  "categoryId": 100012,
  "name": "Apple iPhone 8 (A1863) 64GB 深空灰色 移动联通电信4G手机",
  "subtitle": "【iPhone新品上市】新一代iPhone，让智能看起来更不一样",
  "mainImage": "iphone8.png",
  "subImages": "iphone2.png",
  "detail": "iphone-detail.jpg",
  "price": 5888,
  "stock": 9991,
  "rate": 4.7,
  "status": 1,
  "createTime": "2017-09-23 15:42:52",
  "updateTime": "2017-09-23 15:42:55",
  "imageHost": "http://image.vuespringdemo.com/",
  "parentCategoryId": 0
   }
  }
  ```


### 3.4 cart（购物车相关）

#### 3.4.1 添加商品到购物车

- 接口

  ```
  http://backend.vuespringdemo.com/cart/add.do
  ```
- 请求方式 

  ```
  POST
  ```
- 参数

  ```
  productId=26
  quantity=2
  ```
- 返回值

  ```
  {
  "status": 0,
  "msg": "SUCCESS"
  }
  ```


#### 3.4.2 查看购物车列表

- 接口

  ```
  http://backend.vuespringdemo.com/cart/list.do
  ```

  ​

- 请求方式 

  ```
  GET
  ```

  ​


- 参数

  ```
  无
  ```

  ​


- 返回值





### 3.5 order（订单相关）

#### 3.5.1 创建新订单

- 接口

  ```
  http://backend.vuespringdemo.com/order/create.do
  ```

  ​


- 请求方式 

  ```
  POST
  ```

  ​


- 参数

  ```
  shippingId=30
  ```

  ​


- 返回值

  ```
  {
    "status": 0,
    "data":{
    "orderNo": 1506955845058,
    "payment": 69414,
    "paymentType": 1,
    "paymentTypeDesc": "在线支付",
    "postage": 0,
    "status": 10,
    "statusDesc": "未付款",
    "paymentTime": "",
    "sendTime": "",
    "endTime": "",
    "closeTime": "",
    "createTime": "",
    "orderItemVoList":[
    {"orderNo": 1506955845058, "productId": 26, "productName": null, "productImage": "iphone8.png",…},
    {"orderNo": 1506955845058, "productId": 28, "productName": null, "productImage": "smartian-u2.png",…}
    ],
    "imageHost": "http://image.vuespringdemo.com/",
    "shippingId": 30,
    "receiverName": "无法长大",
    "shippingVo":{"id": 30, "receiverName": "无法长大", "receiverPhone": "18764397221", "receiverMobile": "18764397221",…}
    }
  }
  ```

  ​



