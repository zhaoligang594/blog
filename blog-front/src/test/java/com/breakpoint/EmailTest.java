package com.breakpoint;

import com.email.EmailService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/05/15
 */
public class EmailTest extends BlogFrontApplicationTests {

    @Resource
    private EmailService emailService;

    @Test
    public void test() throws Exception{
        //emailService.sendEmail();
    }
}
