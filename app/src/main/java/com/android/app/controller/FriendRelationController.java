package com.android.app.controller;

import com.android.app.entity.UserTable;
import com.android.app.service.FriendRelationService;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 朋友关系的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/02
 */
@RestController
@RequestMapping("/app/v1/relation")
public class FriendRelationController {

    /**
     * 朋友关系的基本操作
     */
    @Resource
    private FriendRelationService relationService;

    /**
     * 查询自己的所有好友
     * @param userTable
     * @return
     */
    @AccessLimit
    @GetMapping("/getFriends")
    public Object getFriends(UserTable userTable) {
        try {
            /**
             * 用户没有登陆的情况下
             */
            if (null == userTable) {
                return null;
            }

            /**
             * 修改密码的基本操作
             */
            Object o = relationService.getFriends(userTable.getuId());
            return ResponseResult.createOK(o);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 查询好友  以及添加好友
     *
     * @param userKey
     * @return
     */
    @AccessLimit
    @GetMapping("/searchFriend")
    public Object searchFriend(@RequestParam("userKey") String userKey) {
        try {
            /**
             * 修改密码的基本操作
             */
            Object o = relationService.searchFriend(userKey);
            return ResponseResult.createOK(o);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 添加好友的操作
     *
     * @param userTable
     * @param userKey
     * @return
     */
    @AccessLimit
    @PutMapping("/addFriend")
    public Object addFriend(UserTable userTable, @RequestParam("userKey") long userKey) {
        try {
            /**
             * 修改密码的基本操作
             */
            Object o = relationService.addFriend(userTable, userKey);
            return ResponseResult.createOK(o);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 删除用户  一方删除  就删除了
     *
     * @param userTable
     * @param userKey
     * @return
     */
    @AccessLimit
    @PutMapping("/deleteFriend")
    public Object deleteFriend(UserTable userTable, @RequestParam("userKey") String userKey) {
        try {
            /**
             * 修改密码的基本操作
             */
            Object o = relationService.deleteFriend(userTable, userKey);
            return ResponseResult.createOK(o);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
