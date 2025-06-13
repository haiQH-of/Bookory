package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.BookAuthor;
import com.bookoryteam.bookory.model.BookAuthorId;
import com.bookoryteam.bookory.repository.BookAuthorRepository;
import com.bookoryteam.bookory.service.BookAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public BookAuthor save(BookAuthor bookAuthor) {
        return bookAuthorRepository.save(bookAuthor);
    }

    @Override
    public Optional<BookAuthor> findById(BookAuthorId id) {
        return bookAuthorRepository.findById(id);
    }

    @Override
    public List<BookAuthor> findAll() {
        return bookAuthorRepository.findAll();
    }

    @Override
    public void deleteById(BookAuthorId id) {
        bookAuthorRepository.deleteById(id);
    }

    @Override
    public List<BookAuthor> findByBookId(Long bookId) {
        return bookAuthorRepository.findByIdBookId(bookId);
    }

    @Override
    public List<BookAuthor> findByAuthorId(Long authorId) {
        return bookAuthorRepository.findByIdAuthorId(authorId);
    }
}