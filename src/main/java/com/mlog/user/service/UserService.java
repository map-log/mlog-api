package com.mlog.user.service;

import com.mlog.error.UnauthorizedException;
import com.mlog.user.dto.UserDTO;
import com.mlog.user.entity.User;
import com.mlog.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User loginProcess(String email, String password) {
        checkNotNull(email, "email must be provided");
        checkNotNull(password, "password must be provided");

        User data = userMapper.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));
        if (!data.getPassword().equals(password)) {
            throw new UnauthorizedException("Invalid password");
        }
        return data;
    }

    public User findById(Long id) {
        checkNotNull(id, "id must be provided");

        return userMapper.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Invalid id"));
    }

    public User join(User user) {
        if (userMapper.findByEmail(user.getEmail()).isPresent()) {
            throw new UnauthorizedException("User email already exists");
        }

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
                .name(userDTO.getName() == null ? userDTO.getName() : null)
                .email(userDTO.getEmail())
                .role("ROLE_USER")
                .updatedAt(userDTO.getUpdatedAt())
                .createdAt(userDTO.getCreatedAt())
                .build());

        return true;
    }
}
