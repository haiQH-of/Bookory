package com.bookoryteam.bookory.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "BookCategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCategory {

    @EmbeddedId
    private BookCategoryId id;

    @ManyToOne
    @MapsId("bookId") // Ánh xạ bookId từ EmbeddedId
    @JoinColumn(name = "bookId")
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM book VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM book VÀO toString
    private Book book;

    @ManyToOne
    @MapsId("categoryId") // Ánh xạ categoryId từ EmbeddedId
    @JoinColumn(name = "categoryId")
    @EqualsAndHashCode.Exclude // KHÔNG BAO GỒM category VÀO equals/hashCode
    @ToString.Exclude // KHÔNG BAO GỒM category VÀO toString
    private Category category;
}