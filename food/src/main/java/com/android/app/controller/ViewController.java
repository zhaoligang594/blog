package com.android.app.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.service.ViewService;
import com.redis.LocalRedisOpt;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private LocalRedisOpt localRedisOpt;

    @Resource
    private ViewService viewService;

    @AccessLimit(isLogIn = false)
    @RequestMapping("/")
    public String main() {
        return "main";
    }


    @AccessLimit(isLogIn = false)
    @GetMapping(value = "/{viewName}", produces = "html/text")
    @ResponseBody
    public Object signin(@PathVariable("viewName") String viewName, Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response, PageInfo pageInfo) {
        /**
         * 基本操作
         */
        try {
            /**
             * 设置值
             */
            String viewNameTerminal = setModel(viewName, model, request,pageInfo);
            return getProcessString(viewNameTerminal, request, response, model);
        }catch (FoodSiteException e){
            //返回操作失败
            return ResponseResult.createFailResult("操作失败",e.getMessage());
        }

    }


//    @AccessLimit()
//    @GetMapping(value = "/test", produces = "html/text")
//    @ResponseBody
//    public String test(Map<String, Object> model
//            , HttpServletRequest request, HttpServletResponse response,PageInfo pageInfo) {
//        /**
//         * 设置值
//         */
//        String viewNameTerminal = setModel("test", model, request,pageInfo);
//        return getProcessString(viewNameTerminal, request, response, model);
//    }


    private String getProcessString(String pathPage, HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> model) {
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model, applicationContext);
        String process = thymeleafViewResolver.getTemplateEngine().process(pathPage, springWebContext);
        return process;
    }


    /**
     * 设置值基本操作
     *
     * @param viewName 视图名称
     * @param model    返回类
     * @param request  请求对象
     * @param pageInfo 页的基本信息
     * @return
     */
    private String setModel(String viewName, Map<String, Object> model, HttpServletRequest request, PageInfo pageInfo)
            throws FoodSiteException {

        switch (viewName) {
            case "tuijian": {
                PageInfo pageInfo1 = viewService.searchAllTuiJianByPage(pageInfo);
                model.put("pageInfo",pageInfo);
                break;
            }
            case "shangdian": {
                break;
            }case "foodDesc": {
                break;
            }
            case "liuyan": {
                break;
            }
            case "touch": {
                break;
            }case "space": {
                break;
            }case "register": {
                break;
            }
            case "backlogin": {

                String requestURI = request.getRequestURI();
                System.out.println(requestURI);
                //localRedisOpt.set();
                break;
            }
            default: {
                viewName = "index";
                break;
            }
        }

        return viewName;

    }

}
