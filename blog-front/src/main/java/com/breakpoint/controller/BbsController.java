package com.breakpoint.controller;

import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.BbsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网站论坛的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/09
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/bbs")
public class BbsController {

    /**
     * bbs的基本操作
     */
    @Resource
    private BbsService bbsService;


    /**
     * 新增加 文章
     *
     * @return
     */
    @PostMapping("/saveBbs")
    public Object saveBbs(@RequestParam("bbsTitle") String bbsTitle,
                          @RequestParam("bbsDesc") String bbsDesc,
                          @RequestParam("bbsText") String bbsText,
                          @RequestParam("bbsType") String bbsType,
                          LoginUserDto loginUserDto) {


        if (null == loginUserDto) {
            return null;
        }

        try {
            Object insert = bbsService.insert(bbsTitle, bbsDesc, bbsText, bbsType, loginUserDto);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    @GetMapping("/selectByDateDesc")
    public Object selectByDateDesc() {

        try {
            Object insert = bbsService.selectByDateDesc();
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    @GetMapping("/show")
    public Object show(@RequestParam("bbsId") Long bbsId) {

        try {
            Object insert = bbsService.selectOneById(bbsId);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    /**
     * 首页查询
     *
     * @param pageInfo
     * @return
     */
    @GetMapping("/getBbsByPageInfo")
    public Object getBbsByPageInfo(PageInfo pageInfo) {
        try {
            pageInfo = bbsService.getBbsByPageInfo(pageInfo, null);
        } catch (BlogException e) {
            e.printStackTrace();
        }
        return pageInfo;
    }


    @PostMapping("/updateBbs")
    public Object updateTopic(@RequestParam("bbsId") Long bbsId,
                              @RequestParam("bbsTitle") String bbsTitle,
                              @RequestParam("bbsDesc") String bbsDesc,
                              @RequestParam("bbsText") String bbsText,
                              @RequestParam("bbsType") String bbsType,
                              LoginUserDto loginUserDto) {
        /**
         * 用户没有等路
         */
        if (null == loginUserDto) {
            return null;
        }

        try {
            Object updateTopic = bbsService.updateBbs(bbsId, bbsTitle, bbsDesc, bbsText, bbsType, loginUserDto);
            return ResponseResult.createOK(updateTopic);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
