package com.email.impl;

import com.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/06/12
 */
@Slf4j
@Service("shellEmailService")
public class ShellEmailServiceImpl implements EmailService {

    /**
     * 通过命令来进行发送邮件  echo "邮件主题" |mail -s "邮件内容,这是测试邮件" zlgtop@163.com
     *
     * @param subject
     * @param receiveEmail
     * @param content
     * @throws Exception
     */
    @Override
    public void sendEmail(String subject, String receiveEmail, String content) throws Exception {

        ProcessBuilder pb = new ProcessBuilder("/developer/server/shell/sendMail",content,subject,receiveEmail);
        //pb.directory(new File("/developer/server/shell/"));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }

        } catch (IOException e) {
            log.info(e.getMessage());
        }
        if (runningStatus != 0) {
        }
        return;

    }
}
