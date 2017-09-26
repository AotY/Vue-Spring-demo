

安装

rpm -qa | grep vsftpd

yum -y install vsftpd



创建虚拟用户

mkdir /ftpfile

useradd ftpuser -d /ftpfile -s /sbin/nologin  //创建用户，没有登陆系统权限

chown -R ftpuser.ftpuser /ftpfile

passwd ftpuser  12345678





配置vsftpd

chroot_list

vi /etc/selinux/config



防火墙配置

vi /etc/selinux/config

vi /etc/sysconfig/iptables



修改selinux

sudo setenfore 0 





相关命令

sudo service vsftpd start

sudo service vsftpd stop

sudo service vsftpd restart

