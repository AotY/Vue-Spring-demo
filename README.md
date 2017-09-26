---
typora-copy-images-to: ipic
---

# Vue-Spring-demo

**目前效果图：（目前v1.0版本）**

![屏幕快照 2017-09-24 09.58.25](https://ws3.sinaimg.cn/large/006tKfTcly1fjuff4vjw6j31kw12vhdu.jpg)




## 配置
1. mysql

   [mysql配置](http://learning.happymmall.com/mysql/mysqlconfig/)

2. vsftpd配置

   [vsftpd配置](http://learning.happymmall.com/vsftpdconfig/)

3. nginx配置

   [nginx配置](http://learning.happymmall.com/nginx/linux_conf/)


4. tomcat

   ```
   下载：
   wget http://mirror.bit.edu.cn/apache/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.tar.gz
   解压：
   tar -xvf apache-tomcat-8.5.20.tar.gz

   配置环境变量
   export CATALINA_HOME = /xx/apache-tomcat-8.5.20(填写自己的路径)

   遇到的问题
   1. tcp6：
   solution 1: 在server.xml(Connector port="8080"节点)添加address="0.0.0.0"
   Solution 2：在bin文件夹下添加setenv.sh文件，输入 JVM_REQUIRED_ARGS="-Djava.awt.headless=true -Datlassian.standalone=JIRA -Dorg.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER=true -Dmail.mime.decodeparameters=true -Djava.net.preferIPv4Stack=true" ，然后保存

   2. 编码：
   在server.xml（Connector port="8080"节点）添加 URIEncoding="UTF-8"
   ```

5. Spring

   ​

6. MyBatis



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

