package com.mlog.user.controller;

import com.mlog.user.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@Controller
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    // 비밀번호 재설정 요청
    @PostMapping("/user/password-reset-request")
    public ApiResult<Boolean> passwordResetRequest(@RequestParam String email) {
        return success(passwordResetService.sendPasswordResetMail(email));
    }

    // 비밀번호 재설정 토큰 검증 및 비밀번호 재설정
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return "redirect:m-log.site";
    }
}
