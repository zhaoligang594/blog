package com.breakpoint.constans;

import lombok.Data;

/**
 * 上传文件的返回的类型
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/13
 */
@Data
public class ResultForImageUploadMd {

    private int success;//0表示成功，其它失败

    private String message;

    private String url;////提示信息 //一般上传失败后返回



}



