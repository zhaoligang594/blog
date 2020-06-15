package com.breakpoint.constans;

import lombok.Data;

/**
 * 上传文件的返回的类型
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/13
 */
@Data
public class ResultForImageUpload {

    private int code;//0表示成功，其它失败

    private String msg;

    private Desc data;////提示信息 //一般上传失败后返回



}



