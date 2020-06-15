package com.breakpoint.dto;

import lombok.Data;

/**
 *
 *
 */
@Data
public class TopicInfo<T> {

    /**
     * 上一个话题
     */
    private T preTopic;
    /**
     * 当前话题
     */
    private T currentTopic;
    /**
     * 下一个话题
     */
    private T nextTopic;

}
