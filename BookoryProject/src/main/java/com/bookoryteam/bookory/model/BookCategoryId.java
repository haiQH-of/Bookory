package com.bookoryteam.bookory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "bookId")
    private Long bookId;

    @Column(name = "categoryId")
    private Long categoryId;
}