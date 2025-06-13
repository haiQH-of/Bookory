package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.BookCategory;
import com.bookoryteam.bookory.model.BookCategoryId; // Import lớp khóa nhúng
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTínhCủaIdObject + ĐiềuKiện)

    // Tìm tất cả các liên kết BookCategory cho một Book cụ thể
    List<BookCategory> findByIdBookId(Long bookId);

    // Tìm tất cả các liên kết BookCategory cho một Category cụ thể
    List<BookCategory> findByIdCategoryId(Long categoryId);
}