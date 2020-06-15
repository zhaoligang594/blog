package com.breakpoint.service.impl;

import com.breakpoint.dao.BlogUserMapper;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.entity.BlogUser;
import com.breakpoint.entity.BlogUserExample;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.LoginService;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.LocalMD5Utils;
import com.breakpoint.util.PassWordUtils;
import com.breakpoint.util.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private BlogUserMapper blogUserMapper;


    @Resource
    private RedisTemplate redisTemplate;


    /**
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String checkUserLogin(String username, String password) throws BlogException {

        if (StringUtils.isEmpty(username)) {
            log.error("用户名不能为空");
            throw new BlogException("用户名不能为空");
        }

        if (StringUtils.isEmpty(password)) {
            log.error("密码不能为空");
            throw new BlogException("密码不能为空");
        }
        BlogUserExample blogUserExample = new BlogUserExample();


        blogUserExample.createCriteria().andUserNameEqualTo(username.trim());

        List<BlogUser> blogUsers = blogUserMapper.selectByExample(blogUserExample);

        if (null != blogUsers && blogUsers.size() > 0) {

            BlogUser blogUser = blogUsers.get(0);

            /**
             *  邮箱没有激活
             */
            if (blogUser.getUserRole() < 0) {
                throw new BlogException("邮箱没有激活");
            }

            /**
             * 获取到用户的数据库的密码¬
             */
            String dbPassWord = PassWordUtils.getDbPassWord(password, blogUser.getUserSalt());

            if (dbPassWord.equals(blogUser.getUserPassword())) {
                String tokenKey = LocalMD5Utils.md5DigestHexString(blogUser.getUserName() + System.currentTimeMillis());
                LoginUserDto loginUserDto = new LoginUserDto();
                loginUserDto.setBlogUser(blogUser);
                UserInfo.setObject(tokenKey, loginUserDto, redisTemplate);
                return tokenKey;
            } else {
                log.error("密码不正确");
                throw new BlogException("密码不正确");
            }

        } else {
            log.error("没有该用户");
            throw new BlogException("没有该用户");
        }
    }
}
