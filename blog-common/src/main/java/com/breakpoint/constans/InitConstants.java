package com.breakpoint.constans;


/**
 * 基本的常量
 */
public interface InitConstants {

    /**
     * 照片的前缀
     */
    String PICTURE_PREFIX = "http://file.breakpoint.vip/picture/blog/";

    String APP_VERIFY_KEY = "suduyadasfvcsasadasghbjsahkashkahskdhu772w288";

    String EMAIL_REGEX = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";


    /*  邮件的基本初始值    */


    String EMAIL_ACTIVE_SUBJECT = "激活邮件";

    String SITE_PREFIX="http://blog.breakpoint.vip/";

    String EMAIL_ACTIVE_CONTEXT_PREFIX = "欢迎来到日常杂论大家庭，下面是你的账户激活连接，如果不能跳转，请复制到浏览器里面请求\n";


    /*   password   */

    String ORI_PASSWORD="123456";


    /*   角色的值   */

    int NONE_ACTIVE=-1;


    int NORMAL_USER=0;

    int ADMIN=5;

    int SYS_ADMIN=10;


    /*   网站提示信息   */


    String device="请使用电脑登陆该网站";


    String OK_MESSAGE="执行成功：OK";


}
