package com.bookoryteam.bookory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId; // Quan trọng cho OneToOne sử dụng Shared Primary Key
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @Column(name = "userId") // Đảm bảo JPA biết đây là cột userId trong bảng Sellers
    private Long userId;

    @OneToOne 
    @MapsId // Chỉ định rằng userId là cả Primary Key và Foreign Key,đồng thời ánh xạ nó với Primary Key của User.
    @JoinColumn(name = "userId") // Tên cột khóa ngoại trong bảng Sellers (cũng là PK ở đây)
    private User user; // Đối tượng User mà Seller này liên kết

    @Column(name = "companyName", length = 255)
    private String companyName;

    @Column(name = "bankAccountNum", length = 100)
    private String bankAccountNum;

    @Column(name = "bankName", length = 100)
    private String bankName;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}