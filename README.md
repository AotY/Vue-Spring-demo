---
typora-copy-images-to: ipic
---

# Vue-Spring-demo

**目前效果图：（目前v2.0版本）**

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

## 1. 环境配置

### 1.1 安装CentOS (安装时用户名：xjtu，密码：xjtuxjtu)

1. 下载地址

   http://mirrors.aliyun.com/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-1708.iso

2. 在虚拟机中安装CentOS

3. 安装成功后配置阿里云Linux安装软件镜像源（为了提高安装软件速度）

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
### 1.2 selinux配置

```
sudo wget -O /etc/sysconfig/selinux https://github.com/AotY/Vue-Spring-demo/blob/master/config/selinux/config

sudo setenfore 0
```



### 1.3 配置iptables

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




### 1.4 Java配置

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




### 1.5 Tomcat配置

1. 下载安装

   ```
   wget http://mirror.bit.edu.cn/apache/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.tar.gz

   tar -xvf apache-tomcat-8.5.20.tar.gz

   sudo mkdir /developer

   sudo chown -R xjtu.xjtu /ftpfile

   mv apache-tomcat-8.5.20 /developer
   ```

   ​

2. 配置

   ```
   wget -O /developer/apache-tomcat-8.5.20/conf/server.xml https://github.com/AotY/Vue-Spring-demo/blob/master/config/tomcat/server.xml

   wget -O /developer/apache-tomcat-8.5.20/bin/setenv.sh https://github.com/AotY/Vue-Spring-demo/blob/master/config/tomcat/setenv.sh
   ```

   ​

3. 启动


   ```
   sudo /developer/apache-tomcat-8.5.20/bin/startup.sh
   ```



### 1.6 安装Maven

```
wget http://learning.happymmall.com/maven/apache-maven-3.0.5-bin.tar.gz

tar -xvf apache-maven-3.0.5

mv maven /developer
```



### 1.7 安装git

1. 下载安装

   ```
   sudo yum -y install git 
   ```

2. 下载项目代码

   ```
   sudo mkdir /developer/git-repository

   cd /developer/git-repository

   git clone https://github.com/AotY/Vue-Spring-demo
   ```

   ​

### 1. 8 vsftpd配置

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

   sudo chown -R ftpuser.ftpuser /ftpfile  #赋予ftpuser访问ftpfile文件夹权限

   sudo passwd ftpuser 12345678 #设置ftpuser密码

   2. 配置vsftpd.conf
   sudo wget -O /etc/vsftpd/vsftpd.conf https://github.com/AotY/Vue-Spring-demo/blob/master/config/vsftp/vsftpd.conf
   ```

   ​

3. 启动vsftpd

  ```
  sudo service vsftpd start

  sudo chkconfig vsftpd on # 设置开机自启

  相关命令：
  sudo service vsftpd start
  sudo service vsftpd stop
  sudo service vsftpd restart
  ```

4. 导入项目需要使用的图片

   ```
   sudo yum install -y unzip

   cd /ftpfile/image

   wget https://github.com/AotY/Vue-Spring-demo/blob/master/ftpfile/image.zip

   unzip image.zip
   ```

   ​

### 1.9 nginx配置 

1. 下载安装

   ```
   http://nginx.org/download/nginx-1.12.1.tar.gz

   tar -xvf nginx-1.12.1.tar.gz

   cd nginx-1.12.1

   ./configure --prefix=path 

   make

   sudo make install
   ```

2. 配置

   ```
   cd /usr/local/nginx/conf

   sudo wget -O /etc/vsftpd/vsftpd.conf https://github.com/AotY/Vue-Spring-demo/blob/master/config/nginx/nginx.conf

   sudo mkdir vhost & cd vhost

   sudo wget https://github.com/AotY/Vue-Spring-demo/blob/master/config/nginx/vhost/www.vuespringdemo.com.conf

   sudo wget https://github.com/AotY/Vue-Spring-demo/blob/master/config/nginx/vhost/image.vuespringdemo.com.conf

   sudo wget https://github.com/AotY/Vue-Spring-demo/blob/master/config/nginx/vhost/backend.vuespringdemo.com.conf
   ```

3. 启动nginx

   ```
   cd /usr/local/nginx/sbin
   sudo ./nginx
   ```

   ​


### 1.10 MySQL配置

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
   set password for root@localhost=password('xjtuxjtu');
   3.刷新权限
   flush privileges; 
   ```

5. 新增用户 

   ```
   insert into mysql.user(Host, User, Password) values ("localhost", "xjtu", password("xjtuxjtu"));
   ```

6. 导入数据库

   ```
   cd ~

   wget https://github.com/AotY/Vue-Spring-demo/blob/master/backend/vue_spring_demo.sql

   mysql -u xjtu -p vue_spring_demo < vue_spring_demo.sql
   ```

   ​

7. 赋予用户权限

   ```
   1. 本地权限
   grant all privileges on vue_spring_demo.* to xjtu@localhost identified by 'xjtuxjtu'
   2. 远程权限
   grant all privileges on vue_spring_demo.* to xjtu@'%' identified by 'xxxx';
   3.刷新权限
   flush privileges; 
   ```




### 1.11 Node js配置

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

4. 安装cnpm（为了提高安装依赖包的速度）


   ```
   npm install -g cnpm --registry=https://registry.npm.taobao.org
   ```

   ​

   ​

## 2. 部署

1. 进入项目目录

   ```
   cd /developer/git-repository/Vue-Spring-demo
   ```

2. 编译部署

   ```
   ./deploy.sh
   ```

   ​