package com.breakpoint.controller;

import com.breakpoint.constans.ResponseResult;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.WxServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/30
 */
@Slf4j
@RestController
@RequestMapping("/")
public class WxServerController {

    @Resource
    private WxServerService wxServerService;

    @GetMapping("/")
    public Object check(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        try {
            Object o = wxServerService.check(signature, timestamp, nonce, echostr);
            return o;
        } catch (Exception e) {
            //e.printStackTrace();
            return e.getMessage();
        }

    }

    @PostMapping("/")
    public Object wxService(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("openid") String openid,
            @RequestParam("encrypt_type") String encrypt_type,
            @RequestParam("msg_signature") String msg_signature,
            HttpServletRequest request) {
        try {
            //request.get
            Object o = wxServerService.wxService(signature, timestamp, nonce, openid, encrypt_type, msg_signature, request);
            return o;
        } catch (Exception e) {
            //e.printStackTrace();
            return e.getMessage();
        }

    }

    @GetMapping("/getAccessTokenForPublic")
    public Object getAccessTokenForPublic() {
        try {
            //request.get
            Object o = wxServerService.getAccessTokenForPublic();
            return ResponseResult.createOK(o);
            // return o;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }

    }

    @GetMapping("/getJsapiTicketForPublic")
    public Object getJsapiTicketForPublic(@RequestParam("accessToken") String accessToken) {
        try {
            //request.get
            Object o = wxServerService.getJsapiTicketForPublic(accessToken);
            return ResponseResult.createOK(o);
            // return o;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }

    }

    @GetMapping("/signatureForPublic")
    public Object signatureForPublic(@RequestParam("url") String url) {
        try {
            //request.get
            Object o = wxServerService.signatureForPublic(url);
            return ResponseResult.createOK(o);
            // return o;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }

    }


}
