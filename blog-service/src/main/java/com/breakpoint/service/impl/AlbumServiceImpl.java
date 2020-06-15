package com.breakpoint.service.impl;

import com.breakpoint.constans.InitConstants;
import com.breakpoint.dao.TbAlbumMapper;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.entity.TbAlbum;
import com.breakpoint.exception.BaseException;
import com.breakpoint.service.AlbumService;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.LocalVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 相册的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/01/14
 */
@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    /**
     * 注入基本的操作
     */
    @Resource
    private TbAlbumMapper tbAlbumMapper;

    /**
     * 添加一个图片的基本操纵
     *
     * @param loginUserDto
     * @param photoPath
     * @param msg
     * @param location
     * @return
     * @throws BaseException
     */
    @Transactional(rollbackFor = BaseException.class)
    @Override
    public Object addPhoto(LoginUserDto loginUserDto, String photoPath, String msg, String location) throws BaseException {

        /**
         * 校验基本的信息
         */
        LocalVerify.verifyStringIsNotNall(photoPath, msg, location);

        TbAlbum tbAlbum = new TbAlbum();

        tbAlbum.setaId(GenerateIDUtils.getUniqueID36Length());
        tbAlbum.setaPhoto(photoPath);
        tbAlbum.setaMsg(msg);
        tbAlbum.setaLocaltion(location);

        Date nowTime = new Date();

        tbAlbum.setGmtCreate(nowTime);
        tbAlbum.setGmtUpdate(nowTime);

        try {
            tbAlbumMapper.insertSelective(tbAlbum);
            return InitConstants.OK_MESSAGE;
        } catch (Exception e) {
            throw new BaseException("发生错误" + e.getMessage());
        }
    }

    @Override
    public Object updatePhoto(LoginUserDto loginUserDto, String photoPath, String msg, String location) throws BaseException {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Object selectPhotos(PageInfo pageInfo) throws BaseException {

        List<TbAlbum> tbAlbums = tbAlbumMapper.selectByPageListPage(pageInfo);
        pageInfo.setData(tbAlbums);
        return pageInfo;
    }
}
