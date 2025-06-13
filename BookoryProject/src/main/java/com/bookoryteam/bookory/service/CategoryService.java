package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	
	int countCategories();

    // Lưu (tạo hoặc cập nhật) một Category
    Category save(Category category);

    // Tìm Category theo ID
    Optional<Category> findById(Long id);

    // Lấy tất cả Category
    List<Category> findAll();

    // Xóa một Category theo ID
    void deleteById(Long id);

    // Tìm Category theo tên
    Category findByName(String name);

    // Tìm danh sách Category chưa bị xóa mềm
    List<Category> findByDeletedFalse();

    // Đánh dấu một Category là đã xóa mềm
    void softDelete(Long id);
}