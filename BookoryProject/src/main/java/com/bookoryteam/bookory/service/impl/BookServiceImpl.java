package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Book;
import com.bookoryteam.bookory.repository.BookRepository;
import com.bookoryteam.bookory.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByPublisherId(Long publisherId) {
        return bookRepository.findByPublisherId(publisherId);
    }

    @Override
    public List<Book> findBySellerUserId(Long sellerId) {
        return bookRepository.findBySellerUserId(sellerId);
    }

    @Override
    public List<Book> findByPriceBetween(Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Book> findByDeletedFalse() {
        return bookRepository.findByDeletedFalse();
    }

    @Override
    public void softDelete(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setDeleted(true);
            bookRepository.save(book);
        }
    }
    // ✅ THÊM MỚI: Hàm lấy tất cả sách
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // ✅ THÊM MỚI: Hàm thống kê số lượng sách
    @Override
    public int countBooks() {
        return (int) bookRepository.count();
    }

	@Override
	public List<Book> searchByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByStockQuantityGreaterThan(int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findTopByOrderByPublicationYearDesc(int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}
