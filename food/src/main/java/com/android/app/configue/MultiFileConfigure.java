package com.android.app.configue;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
//@Configuration
public class MultiFileConfigure {


    //@Bean
    public CommonsMultipartResolver getCommonsMultipartResolver(){


        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();

        return commonsMultipartResolver;
    }

}
