package com.breakpoint.constans;

/**
 * @Author :zlg
 * @Description : 对返回的code进行统一
 * @Date : Create in 2017/10/12  上午10:54
 * @Modified By :
 */
public enum RetCodeConstant {

    SUCCESS(200, "成功"),
    未登录(300, "未登录"),
    EXCEPTION(400, "请求发生异常"),
    FAIL(500, "失败"),
    TO_MANNY_REQUEST(600, "不能在1s内连续请求该接口"),
    ALERT(700, "弹出提示");
    private int code;
    private String msg;

    private RetCodeConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

}


