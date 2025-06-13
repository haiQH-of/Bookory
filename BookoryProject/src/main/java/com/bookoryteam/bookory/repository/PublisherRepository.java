package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một Publisher theo tên
    Publisher findByName(String name);

    // Tìm danh sách các Publisher chưa bị xóa mềm (deleted = false)
    List<Publisher> findByDeletedFalse();
}