package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một Book theo tiêu đề
    Book findByTitle(String title);

    // Tìm danh sách các Book theo ID của nhà xuất bản (publisherId)
    List<Book> findByPublisherId(Long publisherId);

    // Tìm danh sách các Book theo ID của người bán (sellerId)
    List<Book> findBySellerUserId(Long sellerId); // sellerId trong Book entity là seller.userId

    // Tìm danh sách các Book trong một khoảng giá
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    // Tìm danh sách các Book chưa bị xóa mềm (deleted = false)
    List<Book> findByDeletedFalse();
    
    // Lấy tất cả sách
    List<Book>findAll();
}