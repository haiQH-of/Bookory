package com.bookoryteam.bookory.controller;

import com.bookoryteam.bookory.model.Book;
import com.bookoryteam.bookory.model.Category;
import com.bookoryteam.bookory.service.BookService;
import com.bookoryteam.bookory.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
	// Yêu cầu của Phạm Vĩnh Khang - Giỏ hàng & Trang chủ
	// Yêu cầu của Nguyễn Tấn Duy - Danh sách sách
	
	private final BookService bookService;
	private final CategoryService categoryService;

	public HomeController(BookService bookService, CategoryService categoryService) {
		this.bookService = bookService;
		this.categoryService = categoryService;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<Book> allBooks = bookService.findByDeletedFalse();

		// Lấy sách mới nhất và sách nổi bật
		List<Book> latestBooks = allBooks.stream()
				.sorted(Comparator.comparing(Book::getId).reversed())
				.limit(5)
				.collect(Collectors.toList());

		List<Book> featuredBooks = allBooks.stream()
				.limit(5)
				.collect(Collectors.toList());

		List<Category> categories = categoryService.findByDeletedFalse();

		// Thêm dữ liệu vào Model
		model.addAttribute("latestBooks", latestBooks);
		model.addAttribute("featuredBooks", featuredBooks);
		model.addAttribute("categories", categories);

		return "index";
	}
	
	
	
	@PostMapping("/add-to-cart-from-home")
	public String addToCartFromHome(@RequestParam("bookId") Long bookId,
								  @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
		return "redirect:/cart/add?bookId=" + bookId + "&quantity=" + quantity + "&redirectToHome=true";
	}
}