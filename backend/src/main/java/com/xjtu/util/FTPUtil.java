package com.xjtu.util;

import com.xjtu.common.Const;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by LeonTao on 2017/10/3.
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty(Const.FTP_SERVER_IP);
    private static String ftpUser = PropertiesUtil.getProperty(Const.FTP_USER);
    private static String ftpPass = PropertiesUtil.getProperty(Const.FTP_PASS);
    private static int ftpPort = Integer.valueOf(PropertiesUtil.getProperty(Const.FTP_PORT));
    private static String remotePath = PropertiesUtil.getProperty(Const.FTP_REMOTE_PATH);


    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpUser, ftpPass);
        logger.info("连接FTP服务器");
        boolean result = ftpUtil.uploadFile(remotePath, fileList);
        logger.info("开始连接ftp服务器,结束上传,上传结果:{}");
        return result;
    }


    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean status = true;
        FileInputStream fileInputStream = null;

        if (this.connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(2048);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                for (File file: fileList) {
                    fileInputStream = new FileInputStream(file);
                    getFtpClient().storeFile(file.getName(), fileInputStream);
                }
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                status = false;
                e.printStackTrace();
            } finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }
        return status;

    }

    /**
     *
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     */
    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false;
        this.ftpClient = new FTPClient();
        try {
            ftpClient.connect(this.ip);
            isSuccess = ftpClient.login(this.user, this.pwd);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }


    private String ip;
    private int port;
    private String user;
    private String pwd;

    private FTPClient ftpClient;


    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }


    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
