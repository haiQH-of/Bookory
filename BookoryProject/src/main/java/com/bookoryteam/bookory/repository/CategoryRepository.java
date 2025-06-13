package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một Category theo tên
    Category findByName(String name);

    // Tìm danh sách các Category chưa bị xóa mềm (deleted = false)
    List<Category> findByDeletedFalse();
}