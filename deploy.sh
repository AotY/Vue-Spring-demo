#!/bin/sh

TOMCAT_HOME=/developer/apache-tomcat-8.5.20
GIT_REPOSITORY=/developer/git-repository

echo "部署后端"

echo "1. 进入项目目录"
cd $GIT_REPOSITORY/Vue-Spring-demo

echo "2. git 切换到v1.0分支"
git checkout v1.0

echo "3. git fetch"
git fetch

echo "4. git pull"
git pull

echo "5. 进入后端目录"
cd backend

echo "6. 编译并跳过单元测试"
mvn clean package -Dmaven.test.skip=true

echo "7. 删除旧ROOT.war文件"
rm $TOMCAT_HOME/webapps/ROOT.war 

echo "8. 拷贝编译出来的war包到tomcat下-ROOT.war" 
cp $GIT_REPOSITORY/Vue-Spring-demo/backend/target/vue-spring-demo.war $TOMCAT_HOME/webapps/ROOT.war 


echo "9. 删除tomcat下旧的ROOT文件夹" 
rm -rf $TOMCAT_HOME/webapps/ROOT

echo "10. 关闭tomcat" 
$TOMCAT_HOME/bin/shutdown.sh 

echo "11. sleep 10s" 
for i in {1..10} 
do 
   	echo $i"s" 
   	sleep 1s 
done 

echo "12. 启动tomcat" 
$TOMCAT_HOME/bin/startup.sh 



# echo "部署前端"

# echo "1. 进入项目目录"
# cd $GIT_REPOSITORY/Vue-Spring-demo/fronted

# echo "2. 安装依赖"
# cnpm install

# echo "3. build，生成dist文件夹"
# npm run build 












