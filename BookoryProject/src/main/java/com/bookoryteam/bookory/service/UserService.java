package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	public int countUsers();

    // Phương thức để lưu (tạo hoặc cập nhật) một User
    User save(User user);

    // Phương thức để tìm User theo ID
    Optional<User> findById(Long id);

    // Phương thức để tìm tất cả User
    List<User> findAll();

    // Phương thức để xóa một User theo ID
    void deleteById(Long id);

    // Phương thức để tìm User theo username
    Optional<User> findByUsername(String username);

    // Phương thức để tìm danh sách User theo vai trò
    List<User> findByRole(String role);

    // Phương thức để tìm danh sách User đang hoạt động
    List<User> findByIsActiveTrue();

    // Phương thức để đánh dấu một User là đã xóa mềm
    void softDelete(Long id);
}