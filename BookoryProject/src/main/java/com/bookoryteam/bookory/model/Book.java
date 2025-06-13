package com.bookoryteam.bookory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // Thêm FetchType.LAZY cho ManyToOne
    @JoinColumn(name = "publisherId", nullable = true)
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM publisher VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM publisher VÀO toString
    private Publisher publisher;

    @Column(name = "publicationYear")
    private Integer publicationYear;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "coverImageUrl", length = 500)
    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.LAZY) // Thêm FetchType.LAZY cho ManyToOne
    @JoinColumn(name = "sellerId", nullable = false)
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM seller VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM seller VÀO toString
    private Seller seller;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM collection VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM collection VÀO toString
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM collection VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM collection VÀO toString
    private Set<BookCategory> bookCategories = new HashSet<>();
    
}