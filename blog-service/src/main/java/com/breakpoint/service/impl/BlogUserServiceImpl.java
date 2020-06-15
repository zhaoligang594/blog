package com.breakpoint.service.impl;

import com.breakpoint.constans.InitConstants;
import com.breakpoint.dao.BlogUserMapper;
import com.breakpoint.entity.BlogUser;
import com.breakpoint.entity.BlogUserExample;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.BlogUserService;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.PassWordUtils;
import com.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/11
 */
@Slf4j
@Service
public class BlogUserServiceImpl implements BlogUserService {

    /**
     * 用户的基本操作
     */
    @Resource
    private BlogUserMapper blogUserMapper;


    /**
     * 邮件的基本操作
     */
    @Resource(name = "shellEmailService")
    private EmailService emailService;


    private static final String API_PREFIX = "/active/";

    @Value("${site_prefix}")
    private String site_prefix;


    /**
     * 用户注册
     *
     * @param nickname 昵称
     * @param userName 用户邮箱
     * @return
     * @throws BlogException
     */
    @Transactional(rollbackFor = BlogException.class)
    @Override
    public Object registerUser(String nickname, String userName) throws BlogException {

        /**
         * 校验基本信息
         */

        if (StringUtils.isEmpty(nickname)) {
            throw new BlogException("用户昵称为空");
        }

        if (StringUtils.isEmpty(userName)) {
            throw new BlogException("用户邮箱为空");
        }

        /**
         * 校验邮箱的合法性
         */

        if (!userName.matches(InitConstants.EMAIL_REGEX)) {
            throw new BlogException("邮箱不合法");
        }

        synchronized (BlogUserServiceImpl.class) {
            /**
             * 查询用户
             */
            BlogUserExample blogUserExample = new BlogUserExample();

            blogUserExample.createCriteria().andUserNameEqualTo(userName);


            List<BlogUser> blogUsers = blogUserMapper.selectByExample(blogUserExample);

            if (null != blogUsers && blogUsers.size() > 0) {
                throw new BlogException("邮箱已经注册了");
            }

            /**
             * 没有注册  进行注册
             */

            BlogUser blogUser = new BlogUser();

            blogUser.setUserNo(GenerateIDUtils.generateId());

            blogUser.setUserName(userName);
            blogUser.setNickName(nickname);

            /**
             * 没有激活的状态
             */
            blogUser.setUserRole(InitConstants.NONE_ACTIVE);

            Date nowTime = new Date();

            blogUser.setGmtCreate(nowTime);
            blogUser.setGmtModified(nowTime);

            /**
             * 插入数据库
             */
            try {
                emailService.sendEmail(InitConstants.EMAIL_ACTIVE_SUBJECT, userName,
                        InitConstants.EMAIL_ACTIVE_CONTEXT_PREFIX + site_prefix + API_PREFIX +
                                blogUser.getUserNo() + "/" + blogUser.getUserName());
                blogUserMapper.insertSelective(blogUser);
                return "ok";
            } catch (Exception e) {
                throw new BlogException(e.getMessage());
            }
        }

    }

    @Transactional(rollbackFor = BlogException.class)
    @Override
    public Object active(String userNo, String userName) throws BlogException {

        /**
         * 检测
         */

        if (StringUtils.isEmpty(userNo)) {

            throw new BlogException("userNo==null");
        }

        if (StringUtils.isEmpty(userName)) {

            throw new BlogException("userName==null");
        }

        BlogUserExample userExample = new BlogUserExample();

        userExample.createCriteria()
                .andUserNoEqualTo(Long.valueOf(userNo));

        /**
         * 查询到的用户
         */
        List<BlogUser> blogUsers = blogUserMapper.selectByExample(userExample);

        if (null != blogUsers && blogUsers.size() > 0) {

            /**
             * 查询的用户
             */
            BlogUser blogUser = blogUsers.get(0);

            if (blogUser.getUserRole() >= 0) {
                //说明已经激活了
                throw new BlogException("该用户已经激活，不需要重新激活，请您直接登陆");
            }

            /**
             * 初始化一些信息
             */
            String salt = UUID.randomUUID().toString();

            String dbPassWord = PassWordUtils.getDbPassWord(InitConstants.ORI_PASSWORD, salt);

            /**
             * 普通用户的操作
             */
            blogUser.setUserRole(InitConstants.NORMAL_USER);

            blogUser.setUserSalt(salt);

            blogUser.setUserPassword(dbPassWord);

            Date nowTime = new Date();

            blogUser.setGmtModified(nowTime);

            blogUserMapper.updateByPrimaryKeySelective(blogUser);

            return "OK";

        } else {
            throw new BlogException("激活的用户不存在");
        }
    }
}
