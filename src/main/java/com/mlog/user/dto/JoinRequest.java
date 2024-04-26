package com.mlog.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {

    @NotBlank(message = "name must be provided")
    private String name;

    @NotBlank(message = "email must be provided")
    private String email;

    @NotBlank(message = "password must be provided")
    private String password;

}
