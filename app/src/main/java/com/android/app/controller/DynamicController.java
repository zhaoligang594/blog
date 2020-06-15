package com.android.app.controller;

import com.android.app.entity.UserTable;
import com.android.app.service.DynamicService;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户动态的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
@Slf4j
@RestController
@RequestMapping("/app/v1/dynamic")
public class DynamicController {


    @Resource
    private DynamicService dynamicService;

    /**
     * 用户查询
     *
     * @param pageInfo
     * @param dyType
     * @param userTable
     * @return
     */
    @AccessLimit
    @GetMapping("/searchByPage")
    public Object searchByPage(PageInfo pageInfo, @RequestParam(value = "dyType", required = false, defaultValue = "0") Integer dyType, UserTable userTable) {
        try {
            if (null == userTable) {
                return null;

            }
            /**
             * 修改密码的基本操作
             */
            Object register = dynamicService.searchByPage(pageInfo, dyType, userTable, null);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 查询单个数据
     *
     * @param pageInfo
     * @param dyId
     * @param dyType
     * @param
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping("/searchOne")
    public Object searchOne(PageInfo pageInfo, @RequestParam("dyId") Integer dyId, @RequestParam(value = "dyType", required = false, defaultValue = "0") Integer dyType) {
        try {
            /**
             * 修改密码的基本操作
             */
            Object register = dynamicService.searchByPage(pageInfo, dyType, null, dyId);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 用户空间的动态
     *
     * @param pageInfo
     * @param dyType
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping("/searchAllByPage")
    public Object searchAllByPage(PageInfo pageInfo, @RequestParam(value = "dyType", required = false, defaultValue = "0") int dyType) {
        try {
            Object register = dynamicService.searchByPage(pageInfo, dyType, null, null);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * String dyTitle, String dyText, Integer dyType, UserTable userTable
     *
     * @param userTable
     * @param dyType
     * @return
     */
    @AccessLimit
    @PostMapping("/insertOne")
    public Object insertOne(UserTable userTable,
                            @RequestParam(value = "dyType", required = false, defaultValue = "0") int dyType,
                            @RequestParam(value = "dyTitle") String dyTitle,
                            @RequestParam(value = "dyText") String dyText) {
        try {
            Object register = dynamicService.insertOne(dyTitle, dyText, dyType, userTable);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }
}
