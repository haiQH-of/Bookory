package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.BookAuthor;
import com.bookoryteam.bookory.model.BookAuthorId; // Import lớp khóa nhúng
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm tất cả các liên kết BookAuthor cho một Book cụ thể
    List<BookAuthor> findByIdBookId(Long bookId); // Sử dụng findById<TênThuộcTínhCủaIdObject>

    // Tìm tất cả các liên kết BookAuthor cho một Author cụ thể
    List<BookAuthor> findByIdAuthorId(Long authorId); // Sử dụng findById<TênThuộcTínhCủaIdObject>
}