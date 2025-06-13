package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    // Lưu (tạo hoặc cập nhật) một Publisher
    Publisher save(Publisher publisher);

    // Tìm Publisher theo ID
    Optional<Publisher> findById(Long id);

    // Lấy tất cả Publisher
    List<Publisher> findAll();

    // Xóa một Publisher theo ID
    void deleteById(Long id);

    // Tìm Publisher theo tên
    Publisher findByName(String name);

    // Tìm danh sách Publisher chưa bị xóa mềm
    List<Publisher> findByDeletedFalse();

    // Đánh dấu một Publisher là đã xóa mềm
    void softDelete(Long id);
    //thong
    int countPublishers();

}