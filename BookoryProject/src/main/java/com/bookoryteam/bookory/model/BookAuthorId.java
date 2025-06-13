package com.bookoryteam.bookory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable; // Khóa nhúng phải implement Serializable

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorId implements Serializable {
	private static final long serialVersionUID = 1L;
    @Column(name = "bookId")
    private Long bookId;

    @Column(name = "authorId")
    private Long authorId;
}