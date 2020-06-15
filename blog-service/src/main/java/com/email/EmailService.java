package com.email;


/**
 * 邮件的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/15
 */
public interface EmailService {

    /**
     * 文件内容
     *
     * @param receiveEmail
     * @param content
     * @throws Exception
     */
    void sendEmail(String subject, String receiveEmail, String content) throws Exception;
}
