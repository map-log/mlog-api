package com.mlog.user.dto;

import com.mlog.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserDTO(User user) {
        copyProperties(user, this);
    }

}
