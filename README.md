---
typora-copy-images-to: ipic
---

# Vue-Spring-demo

**目前效果图：（目前v2.0版本）**

- 首页

![index](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvdiq2wj31kw0zkn86.jpg)

- 列表

![](https://ws4.sinaimg.cn/large/006tNc79ly1fjyad61120j31kw0zkn67.jpg)

- 详情

![detail](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvj0yt3j31kw0zkagw.jpg)

- 购物车

![cart](https://ws3.sinaimg.cn/large/006tKfTcly1fk2tvmrwnpj31kw0zk10k.jpg)



## 1. 环境配置

### 1.1 安装CentOS

1. 下载地址

   http://mirrors.aliyun.com/centos/6.8/isos/x86_64/CentOS-6.8-x86_64-bin-DVD1.iso 

2. 安装

3. 配置阿里云Linux安装软件镜像源

   ```
   1. 备份你的原镜像文件
   sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bac
   2. 下载新的CentOS-Base.repo 到/etc/yum.repos.d/
   sudo wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo
   3. 运行yum makecache生成缓存
   sudo yum clean all
   sudo yum makecache
   ```

4. 安装SSH（远程登录）

   ```
   sudo yum -y install openssh-server
   ```

   ​


### 1.2 配置iptables

1. 关闭防火墙

   ```
   sudo systemctl stop firewalld 
   sudo systemctl mask firewalld
   ```

2. 安装iptables

   ```
   sudo yum install iptables-services
   sudo systemctl enable iptables
   ```

3. 配置

   ```
   sudo wget -O /etc/sysconfig/iptables https://github.com/AotY/Vue-Spring-demo/blob/master/config/iptables
   ```

4. 启动

   ```
   sudo service iptables save
   sudo systemctl start iptables
   ```



### 1.3 selinux配置

```
sudo wget -O /etc/sysconfig/selinux https://github.com/AotY/Vue-Spring-demo/blob/master/config/selinux/config
```



### 1.4 MySQL配置

1. 安装

   ```
   1. 查看系统是否安装了MySQL，如果安装了使用(sudo rpm -e --nodeps mysql-*卸载)
   rpm -qa | grep mysql
   2. 安装MySQL
   sudo yum -y install mysql-server
   3. 安装完成后执行初始化命令
   sudo service mysqld start 
   ```

2. 配置

   修改MySQL的配置文件(my.cnf)

   ```
   sudo wget -O /etc/my.cnf https://github.com/AotY/Vue-Spring-demo/blob/master/config/mysql/my.cnf
   重启MySQL服务
   sudo service mysqld restart 
   ```

3. 登录

   ```
   mysql -uroot -p (第一次登录不用密码)
   ```

4. 修改root密码

   ```
   1. 查看用户
   select user, host, password from mysql.user\G;
   2. 设置密码
   set password for root@localhost=password('xxxx');
   3.刷新权限
   flush privileges; 
   ```

5. 新增用户 

   ```
   insert into mysql.user(Host, User, Password) values ("localhost", "qingtao", password("xxxx"));
   ```

6. 新增数据库

   ```
   grant all privileges on vue_spring_demo.* to qintao@localhost identified by 'qingtao'
   ```

7. 赋予用户权限

   ```
   1. 本地权限
   grant all privileges on vue_spring_demo.* to qintao@localhost identified by 'xxxx'
   2. 远程权限
   grant all privileges on vue_spring_demo.* to qingtao@'%' identified by 'xxxx';
   ```


### 1.5 Java配置

1. 下载

   ```
   wget http://learning.happymmall.com/jdk/jdk-7u80-linux-x64.rpm
   ```

2. 安装 (安装默认位置在/usr/java/)

   ```
   sudo rpm -Uvh jdk-7u80-linux-x64.rpm
   ```

3. 配置profile

   ```
   wget wget -O /etc/profile https://github.com/AotY/Vue-Spring-demo/blob/master/config/profile
   ```



### 1.6 安装Maven

```
wget http://learning.happymmall.com/maven/apache-maven-3.0.5-bin.tar.gz
tar -xvf apache-maven-3.0.5
mv apache-maven-3.0.5 maven
mv maven /developer
```



### 1.7 安装git

```
sudo yum -y install git 
```



### 1.6 Tomcat配置

 	1. 下载安装

```
wget http://mirror.bit.edu.cn/apache/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.tar.gz
tar -xvf apache-tomcat-8.5.20.tar.gz
mv apache-tomcat-8.5.20 /developer
```

2. 启动

   ```
   /developer/apache-tomcat-8.5.20/bin/startup.sh
   ```

   ​

> 遇到的问题
>
> 1. tcp6：
>   solution 1: 在server.xml(Connector port="8080"节点)添加address="0.0.0.0"
>   Solution 2：在bin文件夹下添加setenv.sh文件，输入 JVM_REQUIRED_ARGS="-Djava.awt.headless=true -Datlassian.standalone=JIRA -Dorg.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER=true -Dmail.mime.decodeparameters=true -Djava.net.preferIPv4Stack=true" ，然后保存
>
> 2. 编码：
>   在server.xml（Connector port="8080"节点）添加 URIEncoding="UTF-8"



### 1. 8 vsftpd配置

1. [vsftpd配置](http://learning.happymmall.com/vsftpdconfig/)


### 1.9 nginx配置 

[nginx配置](http://learning.happymmall.com/nginx/linux_conf/)



## backend 后端代码

### 项目目录
```
pojo: (Plain Old Java Object)持久化类
```


   ```
   配置图片服务器：
   server {
       listen 80;
       autoindex off;
       server_name image.qingtao.com;
       access_log /usr/local/nginx/logs/access.log combined;
       index index.html index.htm index.jsp index.php;
       #error_page 404 /404.html;
       if ( $query_string ~* ".*[\;'\<\>].*" ){
           return 404;
       }

       location ~ /(mmall_fe|mmall_admin_fe)/dist/view/* {
           deny all;
       }

       location / {
           root /ftpfile/image/;
           add_header Access-Control-Allow-Origin *;
       }
   }

   后端服务器：
   server {
           listen 80;
           autoindex on;
           server_name qingtao.com www.qingtao.com;
           access_log /usr/local/nginx/logs/access.log combined;
           index index.html index.htm index.jsp index.php;
           if ( $query_string ~* ".*[\;'\<\>].*" ){
                   return 404;
                   }

           #location = / {
           #        root /product/front/mmall_fe/dist/view;
           #        index index.html;
           #}

           #location ~ .*\.html$ {
           #        root /product/front/mmall_fe/dist/view;
           #        index index.html;
           #}
           
           location / {
                   proxy_pass http://127.0.0.1:8080/;
                   add_header Access-Control-Allow-Origin *;
           }

           location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|flv|ico)$ {
                   proxy_pass http://127.0.0.1:8080;
                   expires 30d;
           }

           location ~ .*\.(js|css)?$ {
                   proxy_pass http://127.0.0.1:8080;
                   expires 7d;
           }
   }

   前端服务器：
   server {
       listen 80;
       autoindex off;
       server_name s.qingtao.com;
       access_log /usr/local/nginx/logs/access.log combined;
       index index.html index.htm index.jsp index.php;
       if ( $query_string ~* ".*[\;'\<\>].*" ){
           return 404;
       }

       location ~ /(mmall_fe|mmall_admin_fe)/dist/view/* {
           deny all;
       }

       location / {
           root /product/front/;
           add_header Access-Control-Allow-Origin *;
       }
   }
   ```




## fronted 前端代码

### 配置

#### 安装Nodejs

1. 下载安装nodejs

  ```

  wget https://nodejs.org/dist/v6.11.3/node-v6.11.3-linux-x64.tar.xz
  sudo mkdir /usr/local/nodejs
  sudo tar -xJvf node-v6.11.3-linux-x64.tar.xz -C /usr/local/nodejs
  sudo mv /usr/local/nodejs/node-v6.11.3-linux-x64/ /usr/local/nodejs/node-v6.11.3
  ```
2. 配置环境变量
  ```
  export NODEJS_HOME=/usr/local/nodejs/node-v6.11.3
  export PATH=$NODEJS_HOME/bin:$PATH
  ```

3. 验证
  ```
  node -v

  npm version
  ```


#### 安装Ruby

1. 下载源码

   ```
   wget https://cache.ruby-lang.org/pub/ruby/2.4/ruby-2.4.2.tar.gz
   ```

2. 编译安装

   ```
   ./configure
   make
   sudo make install

   By default, this will install Ruby into /usr/local. To change, pass the --prefix=DIR option to the ./configure script.
   ```



### Build Setup

``` bash
# install dependencies
npm install
npm i element-ui -S
npm install axios

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).



### package-locak.json

package-lock.json is automatically generated for any operations where npm modifies either the node_modules tree, or package.json. It describes the exact tree that was generated, such that subsequent installs are able to generate identical trees, regardless of intermediate dependency updates.

