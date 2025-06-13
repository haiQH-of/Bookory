package com.bookoryteam.bookory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime; // Sử dụng OffsetDateTime cho DATETIMEOFFSET
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne	// Mối quan hệ Many-to-One với User
    @JoinColumn(name = "userId", nullable = false) // Cột khóa ngoại trong bảng Orders. Không được NULL.
    private User user; // Đối tượng User liên kết

    @Column(name = "orderDate")
    private OffsetDateTime orderDate; // Sử dụng OffsetDateTime để tương ứng với DATETIMEOFFSET trong SQL Server

    @Column(name = "totalAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount; // Sử dụng Double hoặc BigDecimal cho tiền tệ

    @Column(name = "status", nullable = false, length = 50)
    private String status; // Trạng thái đơn hàng (ví dụ: PENDING, SHIPPED)

    @Column(name = "shippingAddress", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String shippingAddress;

    @Column(name = "paymentMethod", length = 100)
    private String paymentMethod;

    @Column(name = "paymentStatus", nullable = false, length = 50)
    private String paymentStatus;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // Không bao gồm trong equals/hashCode
    @ToString.Exclude // Không bao gồm trong toString
    private List<OrderItem> orderItems;
}