package com.bookoryteam.bookory.service;

import java.util.Optional;

import com.bookoryteam.bookory.model.User;

public interface AuthService {
    // Phương thức xác thực người dùng bằng username và password thô
    boolean authenticateUser(String username, String rawPassword);

    // Phương thức đăng ký người dùng với username và password thô
    void registerUser(String username, String rawPassword);

    // Phương thức kiểm tra tên đăng nhập đã tồn tại chưa
    boolean isUsernameTaken(String username);

    // Phương thức tìm người dùng theo username (để lưu vào session hoặc kiểm tra khác)
    Optional<User> findUserByUsername(String username);
}