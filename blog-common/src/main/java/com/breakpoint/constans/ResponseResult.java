package com.breakpoint.constans;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @author :breakpoint/赵立刚
 * @date : 2017/11/15
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseResult<D> {
    /**
     * 返回的操作码  200：说明操作成功  500：说明操作失败
     */
    private int respCode;
    /**
     * 返回的信息描述
     */
    private String message;
    /**
     * 响应的数据的基本返回
     */
    private D data;
    /**
     * 登录之后返回的路由信息
     */
    private String routing;
    /**
     * jsessionid
     */
    private Serializable jsessionid;


    /**
     * 原来页的跳转
     */
    private String RedirectJS;


    /**
     * 上传的文件的名称
     */
    private String fileName;

    /**
     * 功能编号  1  美食推荐  2  美食商场  3  网站的留言   <font color=red>主要用于网站的基本展示</font>
     */
    private int functionId;


    private ResponseResult() {
    }


    private ResponseResult(int respCode, String message) {
        this.respCode = respCode;
        this.message = message;
    }


    private ResponseResult(int respCode, D data) {
        this.respCode = respCode;
        this.data = data;
    }

    private ResponseResult(int respCode, String message, D data) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
    }


    private ResponseResult(int respCode, String message, int functionId, D data) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
        this.functionId = functionId;
    }

    private ResponseResult(int respCode, String message, D data, String routing, int functionId) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
        this.routing = routing;
        this.functionId = functionId;
    }

    private ResponseResult(int respCode, String message, D data, String routing, Serializable jsessionid) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
        this.routing = routing;
        this.jsessionid = jsessionid;
    }

    public void setRedirectJS(String redirectJS) {
        RedirectJS = redirectJS;
    }

    /**
     * 进行创建成功的与失败的基本操作
     */

    /**
     * 返回默认的成功对象
     *
     * @return
     */
    public static ResponseResult createSuccessResult() {
        return new ResponseResult(RetCodeConstant.SUCCESS.getCode(),
                RetCodeConstant.SUCCESS.getMsg());
    }


    /**
     * 返回带有特定信息的成功对象
     *
     * @return
     */
    public static ResponseResult createSuccessResult(String message) {
        return new ResponseResult(RetCodeConstant.SUCCESS.getCode(), message);
    }


    public static <D> ResponseResult createOK(D data) {
        /**
         * 默认美食推荐
         */
        return createSuccessResult("操作成功", data, FunctionEnum.美食推荐,null);
    }

    public static <D> ResponseResult createOK(D data, String routing) {
        /**
         * 默认美食推荐
         */
        return createSuccessResult("操作成功", data, FunctionEnum.美食推荐, routing);
    }


    public static <D> ResponseResult createOK(D data,String routing, FunctionEnum functionEnum) {
        return createSuccessResult("操作成功", data, functionEnum,routing);
    }


    public static <D> ResponseResult createSuccessResult(String message, D data, FunctionEnum functionEnum, String routing) {
        return new ResponseResult(RetCodeConstant.SUCCESS.getCode(), message, data, routing, functionEnum.getFunctionId());
    }


    /**
     * 返回默认的失败对象
     *
     * @return
     */
    public static ResponseResult createFailResult() {
        return new ResponseResult(RetCodeConstant.FAIL.getCode(),
                RetCodeConstant.FAIL.getMsg());
    }


    /**
     * 返回带有特定信息的失败对象
     *
     * @return
     */
    public static ResponseResult createFailResult(String message) {
        return new ResponseResult(RetCodeConstant.FAIL.getCode(), message);
    }

    /**
     * 返回带有数据的失败对象
     *
     * @param data
     * @param <D>
     * @return
     */
    public static <D> ResponseResult createFailResult(D data) {
        return new ResponseResult(RetCodeConstant.FAIL.getCode(), data);
    }

    /**
     * @param message 返回的信息
     * @param data    返回的数据
     * @param <D>
     * @return
     */
    public static <D> ResponseResult createFailResult(String message, D data) {
        return new ResponseResult(RetCodeConstant.FAIL.getCode(), message, data);
    }

    public static <D> ResponseResult createFail(D data) {
        return createFailResult("操作失败", data);
    }

    /**
     * @param retCodeConstant 返回操作码
     * @param message         返回的信息
     * @param data            返回的数据
     * @param <D>
     * @return
     */
    public static <D> ResponseResult createFailResult(RetCodeConstant retCodeConstant, String message, D data) {
        return new ResponseResult(retCodeConstant.getCode(), message, data);
    }

    public static <D> ResponseResult createFailResult(RetCodeConstant retCodeConstant, String message, D data, Serializable jsessionid) {
        return new ResponseResult(retCodeConstant.getCode(), message, data, null, jsessionid);
    }


    public static <D> ResponseResult createFailResult(String message, D data, String routing, Serializable jsessionid) {
        return new ResponseResult(RetCodeConstant.FAIL.getCode(), message, data, routing, jsessionid);
    }
}
