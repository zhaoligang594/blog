package com.android.app.controller;

import com.android.app.entity.UserTable;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/25
 */
@RestController
@RequestMapping("/app/v1/login")
public class IsLoginController {

    @AccessLimit
    @PostMapping("/islogin")
    public Object searchByPage() {

        return ResponseResult.createOK("ok");

    }

}
