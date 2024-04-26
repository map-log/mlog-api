package com.mlog.user.controller;

import com.mlog.user.dto.JoinRequest;
import com.mlog.user.dto.LoginRequest;
import com.mlog.user.dto.LoginResult;
import com.mlog.user.dto.UserDTO;
import com.mlog.user.entity.User;
import com.mlog.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResult<String> hello() {
        return success("hello user");
    }

    @PostMapping("/login")
    public ApiResult<LoginResult> login(@RequestBody LoginRequest request) {
        User user = userService.loginProcess(request.getPrincipal(), request.getCredentials());
        return success(new LoginResult("tempToken", user));
    }

    @GetMapping("/{id}")
    public ApiResult<UserDTO> findById(@PathVariable Long id) {
        return success(
               new UserDTO(userService.findById(id))
        );
    }

    @PutMapping
    public ApiResult<UserDTO> join(@RequestBody JoinRequest joinRequest) {
        return success(
                new UserDTO(userService.join(
                        new User(joinRequest)
                ))
        );
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return success(userService.delete(id));
    }

    @PatchMapping("/{id}")
    public ApiResult<Boolean> modify(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return success(userService.delete(id));
    }

}
