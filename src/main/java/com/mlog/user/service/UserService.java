package com.mlog.user.service;

import com.mlog.error.UnauthorizedException;
import com.mlog.user.dto.JoinRequest;
import com.mlog.user.dto.LoginRequest;
import com.mlog.user.dto.UserDTO;
import com.mlog.user.entity.User;
import com.mlog.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User loginProcess(String email, String password) {
        checkNotNull(email, "email must be provided");
        checkNotNull(password, "password must be provided");

        User user = userMapper.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));
        user.login(passwordEncoder, password);
        return user;
    }

    public User findById(Long id) {
        checkNotNull(id, "id must be provided");

        return userMapper.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Invalid id"));
    }

    public User joinProcess(JoinRequest joinRequest) {
        if (userMapper.findByEmail(joinRequest.getEmail()).isPresent()) {
            throw new UnauthorizedException("User email already exists");
        }

        User user = User.join(joinRequest, passwordEncoder);
        user.setRole("ROLE_USER");
        userMapper.save(user);

        return userMapper.findByEmail(user.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));
    }

    public boolean delete(Long id) {
        checkNotNull(id, "id must be provided");
        if (userMapper.findById(id).isEmpty()) {
            throw new UnauthorizedException("Invalid id");
        }

        userMapper.findById(id).orElseThrow(() -> new UnauthorizedException("Invalid id"));
        userMapper.delete(id);

        return true;
    }

    public boolean update(UserDTO userDTO, Long id) {
        checkNotNull(id, "id must be provided");
        if (userMapper.findById(id).isEmpty()) {
            throw new UnauthorizedException("Invalid id");
        }

        User data = userMapper.findById(id).orElseThrow(() -> new UnauthorizedException("Invalid id"));
        userMapper.update(User.builder()
                .id(id)
                .name(userDTO.getName() != null ? userDTO.getName() : data.getName())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : data.getEmail())
                .password(userDTO.getPassword() != null ? passwordEncoder.encode(userDTO.getPassword()) : data.getPassword())
                .role("ROLE_USER")
                .build());

        return true;
    }
}
