package com.mlog.user.dto;

import com.mlog.user.entity.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LoginResult {

    private final String token;

    private final UserDTO user;

    public LoginResult(String token, User user) {
        this.token = token;
        this.user = new UserDTO(user);
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("token", token)
                .append("user", user)
                .toString();
    }
}
