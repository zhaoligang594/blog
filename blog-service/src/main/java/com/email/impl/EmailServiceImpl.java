package com.email.impl;

import com.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件的基本操作忘密码的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/15
 */
@Slf4j
@Service("clientEmailService")
public class EmailServiceImpl implements EmailService {

    private static Properties p = new Properties();

    static {
        p.setProperty("mail.smtp.host", "smtp.163.com");
        p.setProperty("mail.smtp.port", "25");
        p.setProperty("mail.smtp.socketFactory.port", "25");
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");

    }

    @Override
    public void sendEmail(String subject, String receiveEmail, String content) throws Exception {

        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zlgtop@163.com", "zhao123");
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        System.out.println("创建邮件");


        //4、创建邮件
        Message message = createSimpleMail(session, subject, receiveEmail, content);

        Transport.send(message);
        //5、发送邮件
    }

    /**
     * @param session
     * @return
     * @throws Exception
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     */
    private MimeMessage createSimpleMail(Session session, String subject, String receiveEmail, String content)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("zlgtop@163.com"));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));
        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }


}
