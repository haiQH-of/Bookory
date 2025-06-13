package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.BookCategory;
import com.bookoryteam.bookory.model.BookCategoryId;
import com.bookoryteam.bookory.repository.BookCategoryRepository;
import com.bookoryteam.bookory.service.BookCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    public BookCategoryServiceImpl(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    @Override
    public BookCategory save(BookCategory bookCategory) {
        return bookCategoryRepository.save(bookCategory);
    }

    @Override
    public Optional<BookCategory> findById(BookCategoryId id) {
        return bookCategoryRepository.findById(id);
    }

    @Override
    public List<BookCategory> findAll() {
        return bookCategoryRepository.findAll();
    }

    @Override
    public void deleteById(BookCategoryId id) {
        bookCategoryRepository.deleteById(id);
    }

    @Override
    public List<BookCategory> findByBookId(Long bookId) {
        return bookCategoryRepository.findByIdBookId(bookId);
    }

    @Override
    public List<BookCategory> findByCategoryId(Long categoryId) {
        return bookCategoryRepository.findByIdCategoryId(categoryId);
    }
}