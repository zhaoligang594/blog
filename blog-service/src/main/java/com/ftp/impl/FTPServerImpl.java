/**
 * Copyright (C), 北京微言巧捷科技有限公司
 * FileName: FTPServerImpl
 * Author:   breakpoint/赵立刚
 * Date:     2018/7/23 下午3:04
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ftp.impl;



import com.ftp.IFTPServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.*;

/**
 * ftp的基本服务 上传以及下载
 *
 * @author :breakpoint/赵立刚
 * @date : 2018/07/23 15:04:46
 */
@Slf4j
@Service("iFTPServer")
public class FTPServerImpl implements IFTPServer {

    /**
     * ftp的host
     */
    @Value("${ftp.host}")
    private String ftpHost;
    /**
     * port
     */
    @Value("${ftp.port}")
    private int ftpPort;
    /**
     * username
     */
    @Value("${ftp.user}")
    private String ftpUser;
    /**
     * password
     */
    @Value("${ftp.password}")
    private String ftpPassword;


    /**
     * FTP服务器基础目录
     */
    @Value("${ftp.basePath}")
    private String basePath;


    private String localPath;

    /**
     * 设置初始值 start
     */
    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * @param filePath     FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename     上传到FTP服务器上的文件名
     * @param locationPath 本地文件全路径
     * @return
     * @throws Exception
     */
    @Override
    public boolean uploadFile(String filePath, String filename, String locationPath) throws Exception {

        try {
            FileInputStream inputStream = new FileInputStream(locationPath);
            return this.uploadFile(filePath, filename, inputStream);
        } catch (FileNotFoundException e) {
            log.error("「{}」文件没有找到 ", locationPath);
            throw new FileNotFoundException(locationPath + "   文件没有找到");
        }
    }

    /**
     * 设置初始值 end
     */
    @Override
    public boolean uploadFile(String filePath, String filename, InputStream input) throws Exception {
        return this.uploadFile(ftpHost, ftpPort, ftpUser, ftpPassword, basePath, filePath, filename, input);
    }


    /**
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param fileName 上传到FTP服务器上的文件名
     * @param input    文件的输入流
     * @return
     * @throws Exception
     */
    @Override
    public boolean uploadFile(String host, int port, String username, String password, String basePath, String filePath,
                              String fileName, InputStream input) throws Exception {
        /**
         * 校验基本的数据
         */
        Assert.notNull(host, "FTP服务器host不能为空");
        Assert.notNull(port, "FTP服务器端口不能为空");
        Assert.notNull(username, "FTP登录账号不能为空");
        Assert.notNull(password, "FTP登录密码不能为空");
        Assert.notNull(basePath, "FTP服务器基础目录不能为空");
        Assert.notNull(filePath, "FTP服务器文件存放路径不能为空");
        Assert.notNull(fileName, "上传到FTP服务器上的文件名不能为空");
        Assert.notNull(input, "文件的输入流不能为null");
        boolean result = false;
        FTPClient ftp = new FTPClient();
        ftp.enterLocalPassiveMode();
        try {
            int reply;
            // 连接FTP服务器
            ftp.connect(host, port);

            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(username, password);
            log.info("登陆成功");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    //进不去目录，说明该目录不存在
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        //创建目录
                        if (!ftp.makeDirectory(tempPath)) {
                            //如果创建文件目录失败，则返回
                            if (log.isErrorEnabled()) {
                                log.error("创建文件目录[{}]失败", tempPath);
                            }

                            return result;
                        } else {
                            //目录存在，则直接进入该目录
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }

            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(fileName, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    @Override
    public boolean downloadFile(String filePath, String fileName) throws Exception {
        return this.downloadFile(ftpHost, ftpPort, ftpUser, ftpPassword, basePath, filePath, fileName, localPath);
    }

    /**
     * @param host      FTP服务器hostname
     * @param port      FTP服务器端口
     * @param username  FTP登录账号
     * @param password  FTP登录密码
     * @param basePath  FTP服务器基础目录
     * @param filePath  基础目录的相对路径
     * @param fileName  要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     * @throws Exception
     */
    @Override
    public boolean downloadFile(String host, int port, String username, String password, String basePath, String filePath,
                                String fileName, String localPath) throws Exception {

        /**
         * 校验基本的数据
         */
        Assert.notNull(host, "FTP服务器host不能为空");
        Assert.notNull(port, "FTP服务器端口不能为空");
        Assert.notNull(username, "FTP登录账号不能为空");
        Assert.notNull(password, "FTP登录密码不能为空");
        Assert.notNull(basePath, "FTP服务器基础目录不能为空");
        Assert.notNull(filePath, "FTP服务器文件存放路径不能为空");
        Assert.notNull(fileName, "要下载的文件名不能为空");
        Assert.notNull(localPath, "下载后保存到本地的路径不能为空");


        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            //登录
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 转移到FTP服务器目录
            /**
             * 如果不能继续不能进行转移，说明不还有该文件夹
             */
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                log.info("不存在您输入文件夹");
                return false;
            }
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    //设置上传文件的类型为二进制类型`
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream os = new FileOutputStream(localFile);

                    ftp.retrieveFile(basePath + filePath + ff.getName(), os);
                    os.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
