package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

    // Tìm một Seller theo userId (khóa chính)
    Optional<Seller> findByUserId(Long userId);

    // Tìm một Seller theo tên công ty (companyName)
    Seller findByCompanyName(String companyName);

    // Tìm danh sách Seller chưa bị xóa mềm (deleted = false)
    List<Seller> findByDeletedFalse();
}