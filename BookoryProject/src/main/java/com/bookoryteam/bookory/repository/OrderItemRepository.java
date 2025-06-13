package com.bookoryteam.bookory.repository;

import com.bookoryteam.bookory.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	// PHƯƠNG THỨC TÌM KIẾM TÙY CHỈNH
	// Spring Data JPA sẽ tự động tạo cài đặt cho các phương thức này
	// dựa trên quy ước đặt tên (ví dụ: findBy + TênThuộcTính + ĐiềuKiện)

	// Tính tổng số lượng của tất cả OrderItem
	@Query("SELECT SUM(oi.quantity) FROM OrderItem oi")
	Integer sumQuantitySold();

	@Query("SELECT SUM(oi.quantity * oi.priceAtPurchase) FROM OrderItem oi")
	Double sumRevenue();

	@Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.order.status = 'COMPLETED'")
	Integer countTotalBooksSold();

	// Tìm danh sách OrderItem theo ID của Order
	List<OrderItem> findByOrderId(Long orderId);

	// Tìm danh sách OrderItem theo ID của Book
	List<OrderItem> findByBookId(Long bookId);

	// Tìm danh sách OrderItem có số lượng lớn hơn hoặc bằng
	List<OrderItem> findByQuantityGreaterThanEqual(Integer quantity);

	// Tìm danh sách OrderItem theo giá tại thời điểm mua
	List<OrderItem> findByPriceAtPurchase(Double priceAtPurchase);
}