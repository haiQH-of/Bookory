package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một User theo tên đăng nhập (username)
	Optional<User>  findByUsername(String username);

    // Tìm danh sách các User theo vai trò (role)
    List<User> findByRole(String role);

    // Tìm danh sách các User đang hoạt động (isActive = true)
    List<User> findByIsActiveTrue();
}