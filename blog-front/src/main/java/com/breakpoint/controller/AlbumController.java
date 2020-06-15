package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.BaseException;
import com.breakpoint.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/01/14
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/album")
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @AccessLimit
    @PostMapping("/addPhoto")
    public Object addPhoto(LoginUserDto loginUserDto,
                           @RequestParam("photoPath") String photoPath,
                           @RequestParam("msg") String msg,
                           @RequestParam("location") String location) {

        try {
            Object o = albumService.addPhoto(loginUserDto, photoPath, msg, location);
            return ResponseResult.createOK(o);
        } catch (BaseException e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 系统后台的基本操作
     *
     * @param loginUserDto 当前的登陆的用户
     * @param pageInfo     分页的信息
     * @return
     */
    @AccessLimit
    @GetMapping("/selectPhotosForBack")
    public Object selectPhotosForBack(LoginUserDto loginUserDto, PageInfo pageInfo) {

        try {
            Object o = albumService.selectPhotos(pageInfo);
            return ResponseResult.createOK(o);
        } catch (BaseException e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    @AccessLimit(isLogIn = false)
    @GetMapping("/selectPhotos")
    public Object selectPhotos(PageInfo pageInfo) {

        try {
            Object o = albumService.selectPhotos(pageInfo);
            return ResponseResult.createOK(o);
        } catch (BaseException e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
