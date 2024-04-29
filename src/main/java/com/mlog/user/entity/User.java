package com.mlog.user.entity;

import com.mlog.user.dto.JoinRequest;
import com.mlog.security.Jwt;
import com.mlog.user.dto.UserDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String email;

    private String name;

    private String password;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(JoinRequest joinRequest) {
        copyProperties(joinRequest, this);
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    public void update(UserDTO userDTO) {
        this.email = userDTO.getEmail() == null ? this.email : userDTO.getEmail();
        this.name = userDTO.getName() == null ? this.name : userDTO.getEmail();
        this.password = userDTO.getEmail() == null ? this.password : userDTO.getEmail();
        this.role = userDTO.getEmail() == null ? this.role : userDTO.getEmail();
        updatedAt = LocalDateTime.now();
    }

    public String newJwt(Jwt jwt, String[] roles) {
        Jwt.Claims claims = Jwt.Claims.of(id, name, roles);
        return jwt.create(claims);
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password)) {
            throw new IllegalArgumentException("Bad credential");
        }
    }

}
