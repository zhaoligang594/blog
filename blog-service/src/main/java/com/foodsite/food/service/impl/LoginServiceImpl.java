package com.foodsite.food.service.impl;

import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.LocalMD5Utils;
import com.foodsite.food.dao.FoodUserMapper;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.entity.FoodUserExample;
import com.foodsite.food.service.LoginService;
import com.redis.BaseRedisSetObject;
import com.redis.LocalRedisOpt;
import com.redis.RedisSetObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户的登陆
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private FoodUserMapper foodUserMapper;

    @Resource
    private LocalRedisOpt localRedisOpt;

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws FoodSiteException
     */
    @Override
    public Object checkFoodUserLogin(String username, String password,String preShowKey) throws FoodSiteException,BaseException {


        if (StringUtils.isEmpty(username)) {
            throw new FoodSiteException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new FoodSiteException("密 码不能为空");
        }
        FoodUserExample footUserExample = new FoodUserExample();
        FoodUserExample.Criteria criteria = footUserExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<FoodUser> footUsers = foodUserMapper.selectByExample(footUserExample);
        if (null == footUsers || footUsers.size() == 0) {
            throw new FoodSiteException("没有该用户");
        }
        FoodUser footUser = footUsers.get(0);
        String userPass = footUser.getUserPass();
        String requestPass = LocalMD5Utils.md5DigestHexString(password);
        if (requestPass.equals(userPass)) {
            String returnKey = UUID.randomUUID().toString();
            BaseRedisSetObject loginRedisSetObject = RedisSetObject.getLoginRedisSetObject(returnKey, footUser);
            localRedisOpt.set(loginRedisSetObject);

            return returnKey;
        } else {
            throw new FoodSiteException("密码错误");
        }

    }


    /**
     * @param username   用户名
     * @param password   密码
     * @param rePassword 确认密码
     * @return
     * @throws FoodSiteException
     */
    @Override
    public Object registerUser(String username, String password, String rePassword) throws FoodSiteException {

        if (StringUtils.isEmpty(username)) {
            throw new FoodSiteException("用户名不能为空");
        }

        if (StringUtils.isEmpty(password)) {
            throw new FoodSiteException("密 码不能为空");
        }

        if (StringUtils.isEmpty(rePassword)) {
            throw new FoodSiteException("确认码不能为空");
        }

        if (!password.equals(rePassword)) {

            throw new FoodSiteException("两次密码不一致");

        }
        FoodUserExample footUserExample=new FoodUserExample();
        footUserExample.createCriteria().andUserNameEqualTo(username);
        synchronized (LoginServiceImpl.class){
            List<FoodUser> footUsers = foodUserMapper.selectByExample(footUserExample);
            if(null==footUsers||footUsers.size()==0){
                String md5Password = LocalMD5Utils.md5DigestHexString(password);
                FoodUser foodUser=new FoodUser();
                foodUser.setFuId(GenerateIDUtils.generateId());
                foodUser.setUserName(username);
                foodUser.setUserPass(md5Password);
                Date date=new Date();
                foodUser.setGmtCreate(date);
                foodUser.setGmtUpdate(date);
                foodUserMapper.insert(foodUser);
                return "注册成功";
            }else {
                throw new FoodSiteException("用户名重复");
            }
        }
    }
}
