package com.bookoryteam.bookory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username; // Tên đăng nhập, không được trùng lặp

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email; // Email, không được trùng lặp

    @Column(name = "passwordHash", nullable = false, length = 255)
    private String passwordHash; // Mật khẩu đã được mã hóa

    @Column(name = "displayName", nullable = false, length = 100)
    private String displayName; // Tên hiển thị

    @Column(name = "role", nullable = false, length = 20)
    private String role; // Vai trò của người dùng (ví dụ: "USER", "ADMIN")

    @Column(name = "isActive", nullable = false)
    private Boolean isActive; // Trạng thái kích hoạt tài khoản (true/false)

    @Column(name = "deleted", nullable = false)
    private Boolean deleted; // Cờ xóa mềm (true=đã xóa, false=chưa xóa)
}