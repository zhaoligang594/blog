package com.breakpoint.service;

import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.BaseException;

/**
 * 相册的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/01/14
 */
public interface AlbumService {

    /**
     * 添加图片的基本操作
     *
     * @param loginUserDto 登陆的用户
     * @param photoPath    照片的路径
     * @param msg          消息
     * @param location     位置信息
     * @return
     * @throws BaseException
     */
    Object addPhoto(LoginUserDto loginUserDto, String photoPath, String msg, String location) throws BaseException;


    /**
     * @param loginUserDto 当前的登陆的用户
     * @param photoPath    照片的路径
     * @param msg          信息
     * @param location     位置信息
     * @return
     * @throws BaseException
     */
    Object updatePhoto(LoginUserDto loginUserDto, String photoPath, String msg, String location) throws BaseException;

    Object selectPhotos(PageInfo pageInfo) throws BaseException;
}
