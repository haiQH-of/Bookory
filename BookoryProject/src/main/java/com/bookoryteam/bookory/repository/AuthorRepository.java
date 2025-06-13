package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một Author theo tên
    Author findByName(String name);

    // Tìm danh sách các Author theo quốc gia
    List<Author> findByCountry(String country);

    // Tìm danh sách các Author chưa bị xóa mềm
    List<Author> findByDeletedFalse();
}