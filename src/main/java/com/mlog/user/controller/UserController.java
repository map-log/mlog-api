package com.mlog.user.controller;

import com.mlog.error.UnauthorizedException;
import com.mlog.security.Jwt;
import com.mlog.security.JwtAuthentication;
import com.mlog.security.JwtAuthenticationToken;
import com.mlog.user.dto.JoinRequest;
import com.mlog.user.dto.LoginRequest;
import com.mlog.user.dto.LoginResult;
import com.mlog.user.dto.UserDTO;
import com.mlog.user.entity.User;
import com.mlog.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Jwt jwt;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping
    public ApiResult<String> hello() {
        return success("hello user");
    }

    @PostMapping("/login")
    public ApiResult<LoginResult> login(@Valid @RequestBody LoginRequest request)
            throws UnauthorizedException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials())
            );
            final User user = (User) authentication.getDetails();
            final String token = user.newJwt(jwt,
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toArray(String[]::new)
            );
            return success(new LoginResult(token, user));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
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
                new UserDTO(userService.joinProcess(joinRequest))
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

    @GetMapping("/me")
    public ApiResult<User> me(
            // JwtAuthenticationTokenFilter 에서 JWT 값을 통해 사용자를 인증한다.
            // 사용자 인증이 정상으로 완료됐다면 @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보(JwtAuthentication)에 접근할 수 있다.
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        if (authentication == null) {
            throw new UnauthorizedException("invalid authentication");
        }
        return success(
                userService.findById(authentication.id)
        );
    }
}
