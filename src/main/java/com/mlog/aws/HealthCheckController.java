package com.mlog.aws;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

import com.mlog.util.ApiUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/health")
    public ApiResult<String> healthCheck() {
        return success("GOOD");
    }
}
