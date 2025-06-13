package com.bookoryteam.bookory.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OrderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne	// Mối quan hệ Many-to-One với Order
    @JoinColumn(name = "orderId", nullable = false) // Cột khóa ngoại trong bảng OrderItems. Không được NULL.
    private Order order;

    @ManyToOne	// Mối quan hệ Many-to-One với Book
    @JoinColumn(name = "bookId", nullable = false) // Cột khóa ngoại trong bảng OrderItems. Không được NULL.
    private Book book; // Đối tượng Book liên kết

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "priceAtPurchase", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase; // Giá sách tại thời điểm mua (quan trọng cho dữ liệu lịch sử)
}