package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.Order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * NOTE: [Đã hoàn thành - Khang]
 * - Đã thêm các phương thức để hỗ trợ tính năng xóa mềm và khôi phục đơn hàng
 * - Bao gồm các API findByDeletedTrue(), findByDeletedFalse() và softDelete()
 */
public interface OrderService {
	
	public int countOrders();
	public int countBooksSold();
	public double getTotalRevenue();

	
	
    // Lưu (tạo hoặc cập nhật) một Order
    Order save(Order order);

    // Tìm Order theo ID
    Optional<Order> findById(Long id);

    // Lấy tất cả Order
    List<Order> findAll();

    // Xóa một Order theo ID
    void deleteById(Long id);

    // Tìm danh sách Order theo ID của người dùng đặt hàng
    List<Order> findByUserId(Long userId);

    // Tìm danh sách Order theo trạng thái
    List<Order> findByStatus(String status);

    // Tìm danh sách Order trong một khoảng thời gian
    List<Order> findByOrderDateBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    /**
     * NOTE: [Đã hoàn thành - Khang]
     * Tìm danh sách Order chưa bị xóa mềm để hiển thị trong trang quản lý đơn hàng chính
     */
    List<Order> findByDeletedFalse();

    /**
     * NOTE: [Đã hoàn thành - Khang]
     * Tìm danh sách Order đã bị xóa mềm để hiển thị trong trang đơn hàng đã xóa
     */
    List<Order> findByDeletedTrue();

    /**
     * NOTE: [Đã hoàn thành - Khang]
     * Đánh dấu một Order là đã xóa mềm thay vì xóa khỏi DB
     */
    void softDelete(Long id);
}

