package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    // Lưu (tạo hoặc cập nhật) một Seller
    Seller save(Seller seller);

    // Tìm Seller theo userId (khóa chính)
    Optional<Seller> findById(Long userId);

    // Lấy tất cả Seller
    List<Seller> findAll();

    // Xóa một Seller theo userId
    void deleteById(Long userId);

    // Tìm Seller theo tên công ty
    Seller findByCompanyName(String companyName);

    // Tìm danh sách Seller chưa bị xóa mềm
    List<Seller> findByDeletedFalse();

    // Đánh dấu một Seller là đã xóa mềm
    void softDelete(Long userId);
}