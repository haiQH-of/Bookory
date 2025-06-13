package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.BookAuthor;
import com.bookoryteam.bookory.model.BookAuthorId; // Cần import lớp ID kép

import java.util.List;
import java.util.Optional;

public interface BookAuthorService {
    // Lưu (tạo hoặc cập nhật) một liên kết BookAuthor
    BookAuthor save(BookAuthor bookAuthor);

    // Tìm liên kết BookAuthor theo ID kép
    Optional<BookAuthor> findById(BookAuthorId id);

    // Lấy tất cả các liên kết BookAuthor
    List<BookAuthor> findAll();

    // Xóa một liên kết BookAuthor theo ID kép
    void deleteById(BookAuthorId id);

    // Tìm tất cả các liên kết của một cuốn sách (theo bookId)
    List<BookAuthor> findByBookId(Long bookId);

    // Tìm tất cả các liên kết của một tác giả (theo authorId)
    List<BookAuthor> findByAuthorId(Long authorId);
}