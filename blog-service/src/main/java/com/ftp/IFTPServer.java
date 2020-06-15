/**
 * Copyright (C), 北京微言巧捷科技有限公司
 * FileName: IFTPServer
 * Author:   breakpoint/赵立刚
 * Date:     2018/7/23 下午3:03
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ftp;

import java.io.InputStream;

/**
 * ftp的基本服务 上传以及下载
 *
 * @author :breakpoint/赵立刚
 * @date : 2018/07/23 15:03:43
 */
public interface IFTPServer {


    /**
     * 利用ftp服务 进行上传文件操作
     *
     * @param filePath     FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename     上传到FTP服务器上的文件名
     * @param locationPath 本地文件全路径
     * @return
     * @throws Exception
     */
    boolean uploadFile(String filePath, String filename, String locationPath) throws Exception;


    /**
     * 使用默认的账号密码等信息通过ftp进行上传的操作
     *
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return boolean 上传成功还是失败 成功返回true 失败返回 false或者抛出异常
     * @throws Exception
     * @author breakpoint/赵立刚
     * @date 2018/7/23   下午3:17
     **/
    boolean uploadFile(String filePath, String filename, InputStream input) throws Exception;


    /**
     * 通过ftp进行上传的操作
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param fileName 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return boolean 上传成功还是失败 成功返回true 失败返回 false或者抛出异常
     * @throws Exception
     * @author breakpoint/赵立刚
     * @date 2018/7/23   下午3:17
     **/
    boolean uploadFile(String host, int port, String username, String password, String basePath,
                       String filePath, String fileName, InputStream input) throws Exception;


    boolean downloadFile(String filePath, String fileName) throws Exception;


    /**
     * 功能描述:<br>
     * 通过ftp下载文件
     *
     * @param host      FTP服务器hostname
     * @param port      FTP服务器端口
     * @param username  FTP登录账号
     * @param password  FTP登录密码
     * @param basePath  FTP服务器基础目录
     * @param filePath  基础目录的相对路径
     * @param fileName  要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return boolean 下载成功还是失败 成功返回true 失败返回 false或者抛出异常
     * @throws Exception
     * @author breakpoint/赵立刚
     * @date 2018/7/23   下午3:17
     **/
    boolean downloadFile(String host, int port, String username, String password, String basePath, String filePath,
                         String fileName, String localPath) throws Exception;

}
