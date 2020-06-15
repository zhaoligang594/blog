package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/topic")
public class TopicController {

    @Resource
    private TopicService topicService;

    /**
     * 新增加 文章
     *
     * @return
     */
    @PostMapping("/save")
    public Object saveTopic(@RequestParam("topicName") String topicTile,
                            @RequestParam("topicDesc") String topicDesc,
                            @RequestParam("test-editor-markdown-doc") String topicText,
                            @RequestParam("topicType") String topicCategory,
                            LoginUserDto loginUserDto) {


        if (null == loginUserDto) {
            return null;
        }

        try {
            Object insert = topicService.insert(topicTile, topicDesc, topicText, topicCategory, loginUserDto);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    @PostMapping("/saveForReact")
    public Object saveForReact(@RequestParam("topicName") String topicTile,
                            @RequestParam("topicDesc") String topicDesc,
                            @RequestParam("topicText") String topicText,
                            @RequestParam("topicType") String topicCategory,
                            LoginUserDto loginUserDto) {


        if (null == loginUserDto) {
            return null;
        }

        try {
            Object insert = topicService.insert(topicTile, topicDesc, topicText, topicCategory, loginUserDto);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }


    @PostMapping("/updateTopicForReact")
    public Object updateTopicForReact(@RequestParam("topicId") Long topicId,
                              @RequestParam("topicName") String topicTile,
                              @RequestParam("topicDesc") String topicDesc,
                              @RequestParam("topicText") String topicText,
                              @RequestParam("topicType") String topicCategory,
                              LoginUserDto loginUserDto) {

        if (null == loginUserDto) {
            return null;
        }

        try {
            Object updateTopic = topicService.updateTopic(topicId, topicTile, topicDesc, topicText, topicCategory, loginUserDto);
            return ResponseResult.createOK(updateTopic);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }




    @GetMapping("/selectByDateDesc")
    public Object selectByDateDesc() {

        try {
            Object insert = topicService.selectByDateDesc();
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    @AccessLimit(isLogIn = false)
    @GetMapping("/show")
    public Object show(@RequestParam("topicId") Long topicId) {

        try {
            Object insert = topicService.selectOneById(topicId);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    @GetMapping("/selectTopicInfoById")
    public Object selectTopicInfoById(@RequestParam("topicId") Long topicId,
                                      @RequestParam(value = "searchTopicType", required = false) String searchTopicType,
                                      @RequestParam(value = "notSearchTopicType", required = false) String notSearchTopicType) {

        try {
            Object insert = topicService.selectTopicInfoById(topicId, searchTopicType, notSearchTopicType);
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
    @GetMapping("/getTopicByPageInfo")
    public Object getTopicByPageInfo(PageInfo pageInfo) {
        try {
            pageInfo = topicService.getTopicByPageInfo(pageInfo, null);

        } catch (BlogException e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @GetMapping("/getAllTopic")
    public Object getAllTopic(PageInfo pageInfo) {
        try {
            pageInfo = topicService.getTopicByPageInfo(pageInfo, null);

            return ResponseResult.createOK(pageInfo);
        } catch (BlogException e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


    @PostMapping("/updateTopic")
    public Object updateTopic(@RequestParam("topicId") Long topicId,
                              @RequestParam("topicName") String topicTile,
                              @RequestParam("topicDesc") String topicDesc,
                              @RequestParam("test-editor-markdown-doc") String topicText,
                              @RequestParam("topicType") String topicCategory,
                              LoginUserDto loginUserDto) {

        if (null == loginUserDto) {
            return null;
        }

        try {
            Object updateTopic = topicService.updateTopic(topicId, topicTile, topicDesc, topicText, topicCategory, loginUserDto);
            return ResponseResult.createOK(updateTopic);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }


    @AccessLimit(isLogIn = false)
    @GetMapping("/getTop3Topic")
    public Object getTop3Topic() {
        try {
            Object updateTopic = topicService.getTop3Topic();
            ResponseResult ok = ResponseResult.createOK(updateTopic);
            return ok;
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 用户空间的操作
     *
     * @param loginUserDto
     * @param pageInfo
     * @return
     */
    @AccessLimit
    @GetMapping("/getAllTopicByUser")
    public Object getAllTopicByUser(LoginUserDto loginUserDto, PageInfo pageInfo) {
        try {
            Object updateTopic = topicService.getTopicByPageInfo(pageInfo, loginUserDto);
            ResponseResult ok = ResponseResult.createOK(updateTopic);
            return ok;
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    @AccessLimit(isLogIn = false)
    @GetMapping("/getAllTopicType")
    public Object getAllTopicType() {
        try {
            Object updateTopic = topicService.getAllTopicType();
            ResponseResult ok = ResponseResult.createOK(updateTopic);
            return ok;
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
