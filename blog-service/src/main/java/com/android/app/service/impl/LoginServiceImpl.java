package com.android.app.service.impl;


import com.android.app.dao.UserTableMapper;
import com.android.app.entity.UserTable;
import com.android.app.entity.UserTableExample;
import com.android.app.service.LoginService;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;
import com.breakpoint.util.LocalMD5Utils;
import com.breakpoint.util.LocalVerify;
import com.redis.AppRedisSetObject;
import com.redis.BaseRedisSetObject;
import com.redis.LocalRedisOpt;
import com.redis.RedisSetObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    /**
     * 用户的基本查询
     */
    @Resource
    private UserTableMapper userTableMapper;


    @Resource
    private LocalRedisOpt localRedisOpt;


    /**
     * 用户的登陆
     *
     * @param username
     * @param password
     * @return
     * @throws AppException
     */
    @Override
    public Object login(String username, String password) throws AppException, BaseException {

        Map<String,Object> returnMap=null;

        /**
         * 验证基本的数据
         */

        LocalVerify.verifyStringIsNotNall(username, password);

        UserTableExample userTableExample = new UserTableExample();

        userTableExample.createCriteria().andUserNameEqualTo(username);

        List<UserTable> userTables = userTableMapper.selectByExample(userTableExample);

        if (null != userTables && userTables.size() > 0) {

            UserTable userTable = userTables.get(0);

            /**
             * 用户没有激活的情况 1是激活的状态
             */
            if (null == userTable.getUserStatus() || 0 == userTable.getUserStatus()) {
                throw new AppException("用户没有激活，请先到邮箱中进行激活");
            }

            String md5Pas = LocalMD5Utils.md5DigestHexString(password);

            if (md5Pas.equals(userTable.getPassword())) {


                String loginToken = userTable.getLoginToken();

                /**
                 * 以前是登陆过的
                 */
                if (!StringUtils.isEmpty(loginToken)) {
                    String key = RedisSetObject.getKey(loginToken);
                    localRedisOpt.expire(key);
                }

                String userKey = UUID.randomUUID().toString();
                BaseRedisSetObject redisSetObject = AppRedisSetObject.getRedisSetObject(userKey, userTable);
                localRedisOpt.set(redisSetObject);
                /**
                 * 存储用户登陆的key
                 */
                userTable.setLoginToken(userKey);
                /**
                 * 更新数据
                 */
                userTableMapper.updateByPrimaryKey(userTable);
                //有用户
                returnMap=new HashMap<>(3);
                returnMap.put("token",userKey);
                returnMap.put("userKey",userTable.getuId());
                returnMap.put("nickName",userTable.getNickName());
                return returnMap;
            } else {
                throw new AppException("密码错误");
            }


        } else {
            //不存在该用户

            throw new AppException("用户不存在");
        }

    }


}
