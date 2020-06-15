package com.breakpoint.controller;

import com.breakpoint.constans.ResponseResult;
import com.breakpoint.exception.BaseException;
import com.breakpoint.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计我们的基本信息
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/03/21
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/statistic")
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @GetMapping("/statisticTopic")
    public Object statisticTopic(){

        try {
            Object o = statisticService.satisticTopic();
            return ResponseResult.createOK(o);
        } catch (BaseException e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }

    }

    @GetMapping("/getTodayDate")
    public Object getTodayDate(){

        try {
            Object o = statisticService.getTodayDate();
            return ResponseResult.createOK(o);
        } catch (BaseException e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }

    }
}
