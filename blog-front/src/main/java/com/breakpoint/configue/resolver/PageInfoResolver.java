package com.breakpoint.configue.resolver;


import com.breakpoint.dto.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/29 0029.
 */
@Slf4j
public class PageInfoResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断参数类型
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PageInfo.class);
    }

    /**
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        /**
         * j
         */
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        PageInfo pageInfo = new PageInfo();
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        String keywords = request.getParameter("keywords");
        String topicType = request.getParameter("topicType");
        pageInfo.setKeyWords(keywords);

        pageInfo.setTopicType(topicType);

        if (StringUtils.isEmpty(keywords)) {
            pageInfo.setKeyWords(null);
        }


        /**
         * 初始化基本操作
         */
        if (StringUtils.isNotEmpty(currentPage)) {
            try {
                pageInfo.setCurrentPage(Integer.valueOf(currentPage));

                if (pageInfo.getCurrentPage() == 1) {
                    pageInfo.setPrePage(1);
                }


            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            pageInfo.setCurrentPage(1);
            pageInfo.setPrePage(1);


        }
        if (StringUtils.isNotEmpty(pageSize)) {
            try {
                pageInfo.setPageSize(Integer.valueOf(pageSize));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            pageInfo.setPageSize(10);
        }

        /*    设置数据的开始和结束的位置  start  */
        int start = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();
        int end = (pageInfo.getCurrentPage()) * pageInfo.getPageSize()+1;
        pageInfo.setDataStart(start);
        pageInfo.setDataEnd(end);
        /*    设置数据的开始和结束的位置  end  */

        return pageInfo;
    }
}
