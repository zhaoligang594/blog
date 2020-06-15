package com.android.app.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.InitConstants;
import com.breakpoint.constans.ResponseResult;
import com.ftp.IFTPServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 文件操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
@Controller
@RequestMapping("/food/v1/file/")
public class FileController {

    @Resource(name = "iFTPServer")
    private IFTPServer iftpServer;

    @AccessLimit(isLogIn = false)
    @PostMapping("/uploadImage")
    @ResponseBody
    public Object imageUpload(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request) throws Exception{

        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        String name = UUID.randomUUID().toString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
        boolean b = iftpServer.uploadFile("", name, file.getInputStream());
        ResponseResult ok = ResponseResult.createOK("OK");
        ok.setFileName(InitConstants.PICTURE_PREFIX+name);
        return ok;

    }
}
