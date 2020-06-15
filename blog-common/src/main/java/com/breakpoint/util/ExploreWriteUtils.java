package com.breakpoint.util;


import com.breakpoint.constans.MimeTypeEnum;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.constans.RetCodeConstant;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 将对象进行性输出
 * Created by Administrator on 2018/4/29 0029.
 */
public class ExploreWriteUtils {


    /**
     * 将信息写会浏览器
     *
     * @param retCodeConstant
     * @param request
     * @param response
     * @param message
     * @throws IOException
     */
    public static void writeMessage(RetCodeConstant retCodeConstant, HttpServletRequest request, HttpServletResponse response, Object message) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("", "GET, POST");
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MimeTypeEnum.APPLICATION_JSON.getMimeType());
        Gson gson = new Gson();
        ResponseResult responseResult = ResponseResult.createFailResult(retCodeConstant, "操作失败", message);
        responseResult.setRedirectJS("");
        String writeMessage = gson.toJson(responseResult);
        String callbackFunName = request.getParameter("callback");
        PrintWriter writer = response.getWriter();
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase("")) {
            writer.println(writeMessage);
        } else {
            writer.println(callbackFunName + "( " + writeMessage + " )");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        writer.close();
        return;
    }
}
