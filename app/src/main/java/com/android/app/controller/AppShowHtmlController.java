package com.android.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.android.app.dao.DynamicTable2Mapper;
import com.android.app.dao.DynamicTableMapper;
import com.android.app.dto.DynamicTableDto;
import com.android.app.dto.DynamicTextDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/02/10
 */
@Slf4j
@Controller
@RequestMapping("/app/v1/show/html")
public class AppShowHtmlController {


    @Resource
    private DynamicTable2Mapper dynamicTable2Mapper;


    @RequestMapping("/{dyId}")
    public String showAppHtml(@PathVariable("dyId") int dyId, Model model) {

        log.info("dyId={}", dyId);
        List<DynamicTableDto> dynamicTableDtos = dynamicTable2Mapper.searchAllByPageAndUser(dyId, null, null, 0, 10);
        if (null != dynamicTableDtos && dynamicTableDtos.size() > 0) {
            DynamicTableDto dynamicTableDto = dynamicTableDtos.get(0);
            model.addAttribute("text", dynamicTableDto);
            String dyText = dynamicTableDto.getDyText();
            if (null == dyText || "".equals(dyText)) {
                dyText = "[]";
            }
            List<DynamicTextDto> list = JSONObject.parseArray(dyText, DynamicTextDto.class);
            model.addAttribute("textList", list);
        } else {
            model.addAttribute("text", null);
        }
        return "app_show";
    }
}
