package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
	
	int countAuthors();

    // Lưu (tạo hoặc cập nhật) một Author
    Author save(Author author);

    // Tìm Author theo ID
    Optional<Author> findById(Long id);

    // Lấy tất cả Author
    List<Author> findAll();

    // Xóa một Author theo ID
    void deleteById(Long id);

    // Tìm Author theo tên
    Author findByName(String name);

    // Tìm danh sách Author theo quốc gia
    List<Author> findByCountry(String country);

    // Tìm danh sách Author chưa bị xóa mềm
    List<Author> findByDeletedFalse();

    // Đánh dấu một Author là đã xóa mềm
    void softDelete(Long id);
}