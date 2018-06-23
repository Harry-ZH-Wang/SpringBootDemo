package com.wzh.config.framework.domain;


import org.apache.ibatis.type.Alias;

/**
 * @author wzh
 * @create 2018-05-27 20:14
 * @desc ${ftp 对象，用于存储ftp账户信息}
 **/
@Alias("ftpBean")
public class FtpBean {

    /**
     * fpt别名
     */
    private String ftpName;

    /**
     * ftp服务器ip
     */
    private String ftpHost;

    /**
     * ftp服务器端口
     */
    private String ftpPort;

    /**
     * 账号名
     */
    private String ftpUserName;

    /**
     * 密码
     */
    private String ftpPassword;

    /**
     * 登录后根目录添加的次级目录
     */
    private String ftpFolder;

    public String getFtpName() {
        return ftpName;
    }

    public void setFtpName(String ftpName) {
        this.ftpName = ftpName;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpFolder() {
        return ftpFolder;
    }

    public void setFtpFolder(String ftpFolder) {
        this.ftpFolder = ftpFolder;
    }

    @Override
    public String toString() {
        return "ftpBean{" +
                "ftpName='" + ftpName + '\'' +
                ", ftpHost='" + ftpHost + '\'' +
                ", ftpPort='" + ftpPort + '\'' +
                ", ftpUserName='" + ftpUserName + '\'' +
                ", ftpPassword='" + ftpPassword + '\'' +
                ", ftpFolder='" + ftpFolder + '\'' +
                '}';
    }
}
