package com.android.app.service.impl;

import com.android.app.dao.UserTableMapper;
import com.android.app.entity.UserTable;
import com.android.app.entity.UserTableExample;
import com.android.app.service.UserService;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.LocalMD5Utils;
import com.breakpoint.util.LocalVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户的基本擦欧总
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    /**
     * 用户表的基本操作
     */
    @Resource
    private UserTableMapper userTableMapper;


    /**
     * 用户注册的基本操作
     *
     * @param userName
     * @param password
     * @param rePassword
     * @return
     * @throws AppException
     */
    @Transactional(rollbackFor = AppException.class)
    @Override
    public Object register(String userName, String password, String rePassword, String nickName) throws BaseException {
        /**
         * 检验基本操作的可行性
         */
        LocalVerify.verifyStringIsNotNall(userName, password, rePassword, nickName);

        if (!password.equals(rePassword)) {
            throw new AppException("两次密码不一致");
        }


        if (!LocalVerify.verifyEmail(userName)) {
            throw new AppException("用户名不是邮箱");
        }

        if (!LocalVerify.verifyPassword(password)) {
            throw new AppException("密码不符合规则");
        }

        /**
         * 检验
         */

        UserTableExample userTableExample = new UserTableExample();

        userTableExample.createCriteria().andUserNameEqualTo(userName);


        /**
         * 新建里用户
         */
        UserTable userTable = new UserTable();
        userTable.setuId(GenerateIDUtils.generateId());
        userTable.setUserName(userName.trim());
        userTable.setPassword(LocalMD5Utils.md5DigestHexString(password.trim()));
        /**
         * 设置权限为5
         */
        userTable.setPermission(5);
        userTable.setNickName(nickName);
        Date nowTime = new Date();
        userTable.setGmtCreate(nowTime);
        userTable.setGmtUpdate(nowTime);
        try {

            /**
             * 插入表哥
             */
            synchronized (this) {
                List<UserTable> userTables = userTableMapper.selectByExample(userTableExample);
                if (null != userTables && userTables.size() > 0) {
                    throw new AppException("该用户已经注册");
                } else {
                    userTableMapper.insert(userTable);
                }
            }
            return "OK";
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }

    }


    /**
     * 重置密码
     *
     * @param userTable   登陆用户
     * @param oriPassword
     * @param password    密码
     * @param rePassword  修改密码
     * @return
     * @throws AppException
     */
    @Override
    public Object reSetPassword(UserTable userTable, String oriPassword, String password, String rePassword) throws BaseException {


        /**
         * 校验基本数据的格式
         */
        LocalVerify.verifyStringIsNotNall(oriPassword, password, rePassword);

        /**
         * 查询操作
         */
        UserTable userTable1 = userTableMapper.selectByPrimaryKey(userTable.getId());
        if (null == userTable1) {

            throw new AppException("没有该用户");
        }
        /**
         * 获取到密码
         */
        String md5Dpassword = LocalMD5Utils.md5DigestHexString(oriPassword);

        if (!md5Dpassword.equals(userTable1.getPassword())) {
            throw new AppException("原密码输入的不正确");
        }

        if (!password.equals(rePassword)) {
            throw new AppException("两次密码输入不一致");
        }

        userTable1.setPassword(LocalMD5Utils.md5DigestHexString(password));
        userTable1.setGmtUpdate(new Date());

        try {
            userTableMapper.updateByPrimaryKeySelective(userTable1);
            return "OK";
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new AppException("修改密码失败");

        }
    }

    /**
     * @param userTable
     * @param password
     * @return
     * @throws AppException
     */
    @Override
    public Object userCancel(UserTable userTable, String password) throws BaseException {
        return null;
    }


}
