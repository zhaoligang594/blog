package com.foodsite.food.service.impl;

import com.android.app.service.FriendRelationService;
import com.breakpoint.exception.AppException;
import com.foodsite.food.AppApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/02/02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendRelationServiceImplTest {

    @Resource
    private FriendRelationService friendRelationService;

    @Test
    public void test()throws AppException {
        Object sajdagjshda = friendRelationService.getFriends(727234235L);

        System.out.println(sajdagjshda);
    }
}
