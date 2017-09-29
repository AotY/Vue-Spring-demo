为了实现跨域访问，同时解决跨域访问时的每次请求session不同的问题，配置如下：



## 前端配置

- main.js

  ```
  import axios from 'axios'
  axios.defaults.withCredentials = true #
  Vue.prototype.$axios = axios
  ```

- Nginx（用于转发到后台Tomcat的请求，Tomcat的端口为8082）

  ```
  server {
          listen 80;
          autoindex on;
          server_name backend.vuespringdemo.com;
          access_log /Library/Logs/nginx/access.log combined;
          index index.html index.htm index.jsp index.php;

          if ( $query_string ~* ".*[\;'\<\>].*" ){
                  return 404;
          }

          location / {
                  proxy_pass http://127.0.0.1:8082/;
                  add_header Access-Control-Allow-Origin http://localhost:8085;
                  add_header Access-Control-Allow-Credentials true;
          }

          location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|flv|ico)$ {
                  proxy_pass http://127.0.0.1:8082;
                  expires 30d;
          }
                  location ~ .*\.(js|css)?$ {
                  proxy_pass http://127.0.0.1:8082;
                  expires 7d;
          }
  }
  ```

  > 注意在withCredentials为true的情况下，Access-Control-Allow-Origin 不能设置为 *。

