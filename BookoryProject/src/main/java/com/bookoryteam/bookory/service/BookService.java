package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {

    // Đếm tổng số sách
    int countBooks();

    // Lưu hoặc cập nhật sách
    Book save(Book book);

    // Tìm sách theo ID
    Optional<Book> findById(Long id);

    // Lấy tất cả sách

    List<Book> findAll();
    
    // Xóa sách theo ID (xóa cứng)
    void deleteById(Long id);

    // Tìm sách theo tiêu đề
    Book findByTitle(String title);

    // Tìm sách theo ID nhà xuất bản
    List<Book> findByPublisherId(Long publisherId);

    // Tìm sách theo ID người bán
    List<Book> findBySellerUserId(Long sellerId);

    // Tìm sách trong khoảng giá
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    // Tìm tất cả sách chưa bị xóa mềm
    List<Book> findByDeletedFalse();

    // Đánh dấu xóa mềm một sách
    void softDelete(Long id);

    // Tìm sách theo từ khóa (nâng cao – có thể mở rộng dùng title hoặc description)
    List<Book> searchByKeyword(String keyword);

    // Lấy danh sách sách theo danh mục ID (nếu có quan hệ với BookCategory)
    List<Book> findByCategoryId(Long categoryId);

    // Lấy danh sách sách còn hàng
    List<Book> findByStockQuantityGreaterThan(int quantity);

    // Lấy danh sách sách mới nhất theo năm xuất bản (giới hạn số lượng)
    List<Book> findTopByOrderByPublicationYearDesc(int limit);
    

}
