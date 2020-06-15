package com.breakpoint.service.impl;

import com.breakpoint.dao.LiuyanMapper;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.entity.Liuyan;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.LiuYanService;
import com.breakpoint.util.LocalVerify;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 网站后台留言的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/04/09
 */
@Service("liuYanService")
public class LiuYanServiceImpl implements LiuYanService {

    /**
     * 网站留言的基本操作
     */
    @Resource
    private LiuyanMapper liuyanMapper;


    @Override
    public PageInfo<Liuyan> getYouLiuYanByPageInfoForSite(PageInfo pageInfo, Object o) throws BlogException {

        List<Liuyan> liuyans = liuyanMapper.selectLiuyanByPageInfoListPage(pageInfo, null, "0");

        pageInfo.setData(liuyans);
        return pageInfo;
    }

    @Override
    public PageInfo<Liuyan> getYouLiuYanByPageInfo(PageInfo pageInfo, Object o) throws BlogException {

        List<Liuyan> liuyans = liuyanMapper.selectLiuyanByPageInfoListPage(pageInfo, null, null);

        pageInfo.setData(liuyans);
        return pageInfo;
    }

    /**
     * @param loginUserDto 当前的登陆用户
     * @param commentId    留言的ID
     * @return
     * @throws BlogException
     */
    @Override
    public Object publicComments(LoginUserDto loginUserDto, String commentId) throws BlogException {

        LocalVerify.verifyStringIsNotNall(commentId);


        try {
            int i = liuyanMapper.publishCommentById(commentId);
            return "OK";
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }
    }

    /**
     * 留言的操作
     *
     * @param userName
     * @param message
     * @return
     */
    @Override
    public Object insert(String userName, String message, String picureUrl) throws BlogException {

        if (StringUtils.isEmpty(message)) {
            throw new BlogException("输入的消息不能为空");
        }

        if (message.length() < 5 || message.length() > 300) {
            throw new BlogException("输入的消息在5到300字之间");
        }


        Date nowTime = new Date();
        Liuyan liuyan = new Liuyan();

        if (StringUtils.isBlank(userName)) {
            userName = "游客";
        }

        liuyan.setUserName(userName);
        liuyan.setMessage(message);

        if (StringUtils.isBlank(picureUrl)) {
            picureUrl = "http://file.breakpoint.vip/picture/blog/4d6fb840-b0b5-46ed-9db8-98084c585229.jpeg";
        }

        liuyan.setPitureUrl(picureUrl);

        liuyan.setGmtCreate(nowTime);
        liuyan.setGmtUpdate(nowTime);
        try {
            liuyanMapper.insertSelective(liuyan);
            return "OK";
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }

    }
}
