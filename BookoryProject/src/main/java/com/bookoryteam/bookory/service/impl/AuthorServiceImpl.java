package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Author;
import com.bookoryteam.bookory.repository.AuthorRepository;
import com.bookoryteam.bookory.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public List<Author> findByCountry(String country) {
        return authorRepository.findByCountry(country);
    }

    @Override
    public List<Author> findByDeletedFalse() {
        return authorRepository.findByDeletedFalse();
    }

    @Override
    public void softDelete(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setDeleted(true);
            authorRepository.save(author);
        }
    }

    // ✅ HÀM THÊM MỚI CHO DASHBOARD
    @Override
    public int countAuthors() {
        return (int) authorRepository.count();
    }
}
