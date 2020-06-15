package com.breakpoint.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询的基本操作
 */
@Data
public class PageInfo<T> implements Serializable {
    /**
     * 上一页
     */
    private int prePage;
    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 数据查询到开始位置
     */
    private int dataStart;

    /**
     * 数据查询的结束位置
     */
    private int dataEnd;

    /**
     * 总共多少页
     */
    private int pageTotal;

    /**
     * 总共多少条
     */
    private int totalCount;

    /**
     * 查询文章的关键字
     */
    private String keyWords;

    /**
     * 查询文章的属性
     */
    private String topicType;

    /**
     * 不包含的对象
     */
    private String notTopicType;
    /**
     * 返回的数据
     */
    private List<T> data;


    public void setPageEndByPageSize(int pageSize){
        this.pageSize=pageSize;
        int start = (currentPage - 1) * pageSize + 1;
        int end = (currentPage) * pageSize;
        this.dataStart=start;
        this.dataEnd=end;
    }


    public void setPageTotalByTotalCount(int totalCount) {

        this.totalCount = totalCount;

        int pages = totalCount / this.pageSize + 1;
        this.pageTotal = pages;

        if (this.currentPage == pages) {
            nextPage = pages;
        } else {
            nextPage = currentPage + 1;
        }

    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", pageTotal=" + pageTotal +
                ", data=" + data +
                '}';
    }
}
