package com.bookoryteam.bookory.service;

import com.bookoryteam.bookory.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

	int sumQuantitySold();

	double sumRevenue();

	int countBooksSold();

	// Lưu (tạo hoặc cập nhật) một OrderItem
	OrderItem save(OrderItem orderItem);

	// Tìm OrderItem theo ID
	Optional<OrderItem> findById(Long id);

	// Lấy tất cả OrderItem
	List<OrderItem> findAll();

	// Xóa một OrderItem theo ID
	void deleteById(Long id);

	// Tìm danh sách OrderItem theo ID của Order
	List<OrderItem> findByOrderId(Long orderId);

	// Tìm danh sách OrderItem theo ID của Book
	List<OrderItem> findByBookId(Long bookId);

	// Tìm danh sách OrderItem có số lượng lớn hơn hoặc bằng một giá trị
	List<OrderItem> findByQuantityGreaterThanEqual(Integer quantity);

	// Tìm danh sách OrderItem theo giá tại thời điểm mua
	List<OrderItem> findByPriceAtPurchase(Double priceAtPurchase);

}