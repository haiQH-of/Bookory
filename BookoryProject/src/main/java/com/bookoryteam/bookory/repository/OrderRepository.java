package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
    // Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
    // dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)
	
	@Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'COMPLETED'")
	Double sumTotalRevenue();


    // Tìm danh sách Order theo ID của người dùng đặt hàng
    List<Order> findByUserId(Long userId);

    // Tìm danh sách Order theo trạng thái (status)
    List<Order> findByStatus(String status);

    // Tìm danh sách Order trong một khoảng thời gian (orderDate)
    List<Order> findByOrderDateBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    // Tìm danh sách các Order chưa bị xóa mềm (deleted = false)
    List<Order> findByDeletedFalse();

    // Tìm danh sách các Order đã bị xóa mềm (deleted = true)
    List<Order> findByDeletedTrue();
}