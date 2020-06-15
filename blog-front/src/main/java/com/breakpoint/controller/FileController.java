package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.*;
import com.ftp.IFTPServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
@Controller
@RequestMapping("/blog/v1/file/")
public class FileController {

    @Resource(name = "iFTPServer")
    private IFTPServer iftpServer;

    @AccessLimit(isLogIn = false)
    @PostMapping("/uploadImage")
    @ResponseBody
    public Object imageUpload(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request) throws Exception {

        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        String name = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        boolean b = iftpServer.uploadFile("", name, file.getInputStream());

        if (b) {
            ResponseResult ok = ResponseResult.createOK("OK");
            ok.setFileName(InitConstants.PICTURE_PREFIX + name);
            return ok;
        } else {
            return ResponseResult.createFail("上传失败");
        }
    }

    @AccessLimit(isLogIn = false)
    @PostMapping("/imageUpload4React")
    @ResponseBody
    public Object imageUpload4React(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request) throws Exception {

        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        String name = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        boolean b = iftpServer.uploadFile("", name, file.getInputStream());

        if (b) {
            Map<String,Object> map=new HashMap<>(2);
            map.put("fileName",InitConstants.PICTURE_PREFIX + name);
            ResponseResult ok = ResponseResult.createOK(map);
            //ok.setFileName(InitConstants.PICTURE_PREFIX + name);
            return ok;
        } else {
            return ResponseResult.createFail("上传失败");
        }
    }





    @AccessLimit(isLogIn = false)
    @PostMapping("/uploadImageForEditor")
    @ResponseBody
    public Object uploadImageForEditor(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request) throws Exception {


        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        String name = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        boolean b = iftpServer.uploadFile("", name, file.getInputStream());


        ResultForImageUpload result=new ResultForImageUpload();


        if (b) {

            result.setCode(0);
            Desc desc = new Desc();
            desc.setSrc(InitConstants.PICTURE_PREFIX + name);

            result.setData(desc);

            return result;
        } else {

            result.setCode(2);
            result.setMsg("上传失败");
            return result;
        }


    }


    @AccessLimit(isLogIn = false)
    @PostMapping("/uploadImageForMd")
    @ResponseBody
    public Object uploadImageForMd(@RequestParam(value = "editormd-image-file", required = true)MultipartFile file,
                              HttpServletRequest request) throws Exception {


        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        String name = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        boolean b = iftpServer.uploadFile("", name, file.getInputStream());


        ResultForImageUploadMd result=new ResultForImageUploadMd();


        if (b) {

            result.setSuccess(1);

            result.setUrl(InitConstants.PICTURE_PREFIX + name);
            result.setMessage("上传成功");
            return result;
        } else {

            result.setSuccess(0);
            result.setMessage("上传失败");
            return result;
        }


    }





}
