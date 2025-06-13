package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.BookCategory;
import com.bookoryteam.bookory.model.BookCategoryId; // Cần import lớp ID kép

import java.util.List;
import java.util.Optional;

public interface BookCategoryService {
    // Lưu (tạo hoặc cập nhật) một liên kết BookCategory
    BookCategory save(BookCategory bookCategory);

    // Tìm liên kết BookCategory theo ID kép
    Optional<BookCategory> findById(BookCategoryId id);

    // Lấy tất cả các liên kết BookCategory
    List<BookCategory> findAll();

    // Xóa một liên kết BookCategory theo ID kép
    void deleteById(BookCategoryId id);

    // Tìm tất cả các liên kết của một cuốn sách (theo bookId)
    List<BookCategory> findByBookId(Long bookId);

    // Tìm tất cả các liên kết của một thể loại (theo categoryId)
    List<BookCategory> findByCategoryId(Long categoryId);
}