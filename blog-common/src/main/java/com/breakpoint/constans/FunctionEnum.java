package com.breakpoint.constans;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/21
 */
public enum  FunctionEnum {


    美食推荐(1,"美食推荐"),
    美食商场(2,"美食商场"),
    网站的留言(3,"网站的留言");
    private int functionId;
    private String desc;
    FunctionEnum(int functionId, String desc) {
        this.functionId = functionId;
        this.desc = desc;
    }
    public int getFunctionId() {
        return functionId;
    }

    public String getDesc() {
        return desc;
    }
}
