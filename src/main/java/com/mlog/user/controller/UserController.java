package com.mlog.user.controller;

import com.mlog.util.ApiUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mlog.util.ApiUtils.success;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ApiUtils.ApiResult<String> hello() {
        return success("hello user");
    }
}
