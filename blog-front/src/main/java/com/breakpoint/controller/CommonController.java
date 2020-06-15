package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.configue.IsLoginUtils;
import com.breakpoint.configue.resolver.AccessMethodParam;
import com.breakpoint.constans.InitConstants;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.BlogTopicDto;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.dto.TopicInfo;
import com.breakpoint.entity.BbsTable;
import com.breakpoint.entity.BlogTopic;
import com.breakpoint.entity.Liuyan;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公共控制层
 *
 * @author Administrator
 */
@Slf4j
@Controller
@RequestMapping("/back")
public class CommonController {

    @Resource
    private TopicService topicService;

    @Resource
    private IsLoginUtils isLoginUtils;

    @Resource
    private YouHuiJuanService youHuiJuanService;

    /**
     * 留言的基本操作
     */
    @Resource
    private LiuYanService liuYanService;

    /**
     * 论坛的基本操作
     */
    @Resource
    private BbsService bbsService;


    @Resource
    private BlogUserService blogUserService;

    /**
     * 网站的首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "type", defaultValue = "index") String type,
                        Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {
        System.out.println(type);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        try {
            //pageInfo = topicService.getTopicByPageInfo(pageInfo, null);
            //model.put("pageInfo", pageInfo);
        } catch (Exception e) {
            return "error";
        }
        return "main";
    }

    @GetMapping("/main")
    public String main(@RequestParam(value = "type", defaultValue = "main") String type,
                       Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo,
                       Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        return "main";
    }

    /**
     * 网站论坛的基本操作
     * <p>
     * 网站论坛的操作
     *
     * @param type
     * @return
     */
    @GetMapping("/bbs")
    public String bbs(@RequestParam(value = "type", defaultValue = "bbs") String type,
                      Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo,
                      Device device) {

        /**
         * 判断是否是手机平板电脑登陆
         */
        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        try {
            pageInfo = bbsService.getBbsByPageInfo(pageInfo, null);
            model.put("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


        return "bbs";
    }


    /**
     * 查看文章的详情
     *
     * @return
     */
    @GetMapping("/topicinner2")
    public String topicinner2(@RequestParam(value = "type", defaultValue = "index") String type,
                              Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, @RequestParam("topicId") Long topicId,
                              @RequestParam(value = "searchTopicType", required = false) String searchTopicType,
                              @RequestParam(value = "notSearchTopicType", required = false) String notSearchTopicType,
                              Device device) {
        System.out.println(type);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        try {

            if (null == topicId) {
                return "404";
            }

            TopicInfo<BlogTopicDto> topicInfo = topicService.selectTopicInfoById(topicId, searchTopicType, notSearchTopicType);

            if (null != topicInfo.getCurrentTopic()) {
                model.put("topicInfo", topicInfo);
            } else {

                return "404";
            }

        } catch (BlogException e) {
            return "404";
        }
        return "topicinner";
    }

    /**
     * 查看文章的详情
     *
     * @return
     */
    @GetMapping("/bbsInner")
    public String bbsInner(@RequestParam(value = "type", defaultValue = "index") String type,
                           Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, @RequestParam("bbsId") Long bbsId,
                           Device device) {
        System.out.println(type);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        try {

            if (null == bbsId) {
                return "404";
            }

            TopicInfo<BbsTable> topicInfo = bbsService.selectTopicInfoById(bbsId);

            if (null != topicInfo.getCurrentTopic()) {
                model.put("topicInfo", topicInfo);
            } else {

                return "404";
            }

        } catch (BlogException e) {
            return "404";
        }
        return "bbsInner";
    }


    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Resource
    private ApplicationContext applicationContext;


    /**
     * 用户登陆成功后的个人空间 获取到自己的发表的文章
     *
     * @return
     */
    @GetMapping(value = "/privateZone")
    public String privateZone(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            try {
                LoginUserDto loginUser = (LoginUserDto) isLoginUtils.getLoginUser(request);
                PageInfo<BlogTopic> topicByPageInfo = topicService.getTopicByPageInfo(pageInfo, loginUser);

                model.put("pageInfo", topicByPageInfo);

            } catch (Exception e) {
                return "error";
            }
            return "privateZone";
        } else {
            return "signin";
        }

    }

    @GetMapping(value = "/albumZone")
    public String albumZone(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";
        }
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            try {
                LoginUserDto loginUser = (LoginUserDto) isLoginUtils.getLoginUser(request);
                PageInfo<BlogTopic> topicByPageInfo = topicService.getTopicByPageInfo(pageInfo, loginUser);
                model.put("pageInfo", topicByPageInfo);
            } catch (Exception e) {
                return "error";
            }
            return "albumZone";
        } else {
            return "signin";
        }

    }


    @GetMapping(value = "/bbsZone")
    public String bbsZone(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            try {

                LoginUserDto loginUser = (LoginUserDto) isLoginUtils.getLoginUser(request);

                PageInfo<BbsTable> topicByPageInfo = bbsService.getBbsByPageInfo(pageInfo, loginUser);

                model.put("pageInfo", topicByPageInfo);

            } catch (Exception e) {
                return "error";
            }
            return "bbsZone";
        } else {
            return "signin";
        }

    }

    /**
     * 编辑BBS
     *
     * @return
     */
    @GetMapping(value = "/editBbs")
    public String editBbs(Map<String, Object> model, HttpServletRequest request, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            return "editBbs";
        } else {
            return "signin";
        }
    }

    @GetMapping(value = "/archives")
    public String archives(Map<String, Object> model, HttpServletRequest request, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        return "archives";
    }

    @GetMapping(value = "/album")
    public String album(Map<String, Object> model, HttpServletRequest request, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        return "album";
    }


    /**
     * 修改米啊的操作
     *
     * @param request
     * @param device
     * @return
     */
    @GetMapping(value = "/passwordZone")
    public String passwordZone(Map<String, Object> model, HttpServletRequest request, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            return "passwordZone";
        } else {
            return "signin";
        }
    }


    /**
     * 管理员的基本操作
     *
     * @param request
     * @param device
     * @return
     */
    @AccessLimit(isLogIn = false, permissionInt = 5)
    @GetMapping(value = "/adminZone")
    public String adminZone(Map<String, Object> model, HttpServletRequest request, Device device, AccessMethodParam accessMethodParam) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {

            if (accessMethodParam.isCanProcess()) {
                return "adminZone";
            } else {
                return "permissionZone";
            }

        } else {
            return "signin";
        }
    }


    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/privateMessageZone")
    public String privateMessageZone(Map<String, Object> model, HttpServletRequest request, Device device, AccessMethodParam accessMethodParam) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            return "privateMessageZone";
        } else {
            return "signin";
        }
    }


    /**
     * 编辑BBS
     *
     * @return
     */
    @GetMapping(value = "/updateBbs")
    public String updateBbs(Map<String, Object> model, HttpServletRequest request, Device device,
                            @RequestParam("bbsId") String bbsId) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {

            model.put("bbsId", bbsId);
            return "updateBbs";
        } else {
            return "signin";
        }
    }


    @GetMapping(value = "/updatetopic")
    public String updatetopic(Map<String, Object> model
            , HttpServletRequest request, @RequestParam("topicId") Long topicId) {
        /**
         * 是否登陆的操作逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            System.out.println(topicId);

            if (null == topicId) {
                return "redirect:privateZone";

            }
            model.put("topicId", topicId);


            //TODO
            return "updatetopic2";
        } else {
            return "signin";
        }

    }


    /**
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/error", produces = "html/text")
    @ResponseBody
    public String error(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response) {
        return getProcessString("404", request, response, model);
    }


    /**
     * 编辑文本
     *
     * @return
     */
    @GetMapping(value = "/edittopic")
    public String edittopic(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response) {
        /**
         * 可以在这里面业务逻辑
         */
        if (isLoginUtils.isLogin(request)) {
            //return "edittopic";
            return "edittopic";
        } else {
            return "signin";
        }

    }


    /*   激活  start   */

    @AccessLimit(isLogIn = false)
    @GetMapping("/active/{userNo}/{userName}")
    public Object active(Map<String, Object> model, @PathVariable("userNo") String userNo,
                         @PathVariable("userName") String userName) {

        try {
            Object o = blogUserService.active(userNo, userName);
            model.put("info", "激活用户成功，请前往登录");
            return "active_fail";
        } catch (Exception e) {
            //e.printStackTrace();
            model.put("info", e.getMessage());
            return "active_fail";
        }
    }



    /*   激活  end   */





    /*    前台的跳转   start    */


    /**
     * 登陆页的基本展示
     *
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/signin")
    public String signin(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device, PageInfo pageInfo) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        if (isLoginUtils.isLogin(request)) {
            try {
                LoginUserDto loginUser = (LoginUserDto) isLoginUtils.getLoginUser(request);
                PageInfo<BlogTopic> topicByPageInfo = topicService.getTopicByPageInfo(pageInfo, loginUser);

                model.put("pageInfo", topicByPageInfo);

            } catch (Exception e) {
                return "error";
            }
            return "privateZone";
        } else {
            return "signin";
        }
    }


    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/signin_alert")
    public String signinAlert(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device, PageInfo pageInfo) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        if (isLoginUtils.isLogin(request)) {
            try {
                LoginUserDto loginUser = (LoginUserDto) isLoginUtils.getLoginUser(request);
                PageInfo<BlogTopic> topicByPageInfo = topicService.getTopicByPageInfo(pageInfo, loginUser);

                model.put("pageInfo", topicByPageInfo);

            } catch (Exception e) {
                return "error";
            }

            model.put("message", "您已经登陆");

            return "message";
        } else {
            return "signin_alert";
        }
    }

    /**
     * 注册操作
     *
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/register")
    public String register(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        return "register";
    }


    /**
     * 首页的操作
     *
     * @return
     */
    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/index", produces = "html/text")
    @GetMapping(value = "/index")
    //@ResponseBody
    public String index(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }

        try {
            pageInfo.setPageSize(6);

            pageInfo.setNotTopicType("life");


            pageInfo = topicService.getTopicByPageInfo(pageInfo, null);
            model.put("pageInfo", pageInfo);

        } catch (BlogException e) {
            return "error";
        }
        return "header";
    }


    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/lifeTime")
    //@ResponseBody
    public String lifeTime(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        try {
            pageInfo.setPageSize(6);
            pageInfo.setTopicType("life");
            pageInfo = topicService.getTopicByPageInfo(pageInfo, null);
            model.put("pageInfo", pageInfo);
        } catch (BlogException e) {
            return "error";
        }
        return "lifeTime";
    }

    /**
     * 优惠卷的基本操作
     *
     * @return
     */


    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/index", produces = "html/text")
    @GetMapping(value = "/youhuijuan")
    //@ResponseBody
    public String youhuijuan(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        if (device.isMobile() || device.isTablet()) {

            return "redirect:myouhuijuan";
        }
        try {
            pageInfo = youHuiJuanService.getYouhuijuanBypage(pageInfo, null);
            model.put("pageInfo", pageInfo);

        } catch (BlogException e) {
            return "error";
        }
        return "youhuijuan";
    }


    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/index", produces = "html/text")
    @GetMapping(value = "/myouhuijuan")
    //@ResponseBody
    public String myouhuijuan(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo, Device device) {

        try {
            pageInfo = youHuiJuanService.getYouhuijuanBypage(pageInfo, null);
            model.put("pageInfo", pageInfo);

        } catch (BlogException e) {
            return "error";
        }
        return "myouhuijuan";
    }


    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/index", produces = "html/text")
    @GetMapping(value = "/liuyan")
    //@ResponseBody
    public String liuyan(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo<Liuyan> pageInfo, Device device) {

        try {
            pageInfo = liuYanService.getYouLiuYanByPageInfoForSite(pageInfo, null);

            model.put("pageInfo", pageInfo);

        } catch (BlogException e) {
            return "error";
        }
        return "liuyan";
    }


    /**
     * lianxiye
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/link", produces = "html/text")
    @GetMapping(value = "/touchMe")
    public String link(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device) {
        //return getProcessString("link", request, response, model);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        return "touchMe";
    }


    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/link", produces = "html/text")
    @GetMapping(value = "/projects")
    public String projects(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device) {
        //return getProcessString("link", request, response, model);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        return "projects";
    }


    @AccessLimit(isLogIn = false)
    //@GetMapping(value = "/link", produces = "html/text")
    @GetMapping(value = "/timeLink")
    public String timeLink(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, Device device) {
        //return getProcessString("link", request, response, model);

        if (device.isMobile() || device.isTablet()) {
            model.put("message", InitConstants.device);
            return "message";

        }
        return "timeLink";
    }


    /*    前台的跳转   end    */


//    /**
//     * 登陆成功页
//     *
//     * @return
//     */
//    @AccessLimit(isLogIn = false)
//    @GetMapping(value = "/success", produces = "html/text")
//    @ResponseBody
//    public String success(Map<String, Object> model
//            , HttpServletRequest request, HttpServletResponse response) {
//        return getProcessString("success", request, response, model);
//    }


    //old
//    @AccessLimit(isLogIn = false)
//    @GetMapping(value = "/topicinner", produces = "html/text")
//    @ResponseBody
//    public String topicinner(Map<String, Object> model
//            , HttpServletRequest request, HttpServletResponse response, @RequestParam("topicId") Long topicId) {
//
//        try {
//            TopicInfo<BlogTopic> topicInfo = topicService.selectTopicInfoById(topicId);
//            model.put("topicInfo", topicInfo);
//            return getProcessString("topicinner", request, response, model);
//        } catch (BlogException e) {
//            return "出现错误，请联系管理员";
//        }
//    }


    private String getProcessString(String pathPage, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model, applicationContext);
        String process = thymeleafViewResolver.getTemplateEngine().process(pathPage, springWebContext);
        return process;
    }

}
