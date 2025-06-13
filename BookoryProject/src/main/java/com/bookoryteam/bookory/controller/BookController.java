package com.bookoryteam.bookory.controller;

import com.bookoryteam.bookory.service.BookService;
import com.bookoryteam.bookory.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @GetMapping("/book-list")
    public String showBookList(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("allBooks", books);
        return "public/book_list"; // Tên file trong templates
    }
}

