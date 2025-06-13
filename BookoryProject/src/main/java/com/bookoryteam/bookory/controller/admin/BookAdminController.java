package com.bookoryteam.bookory.controller.admin;

import java.util.List;
import java.util.Optional;
import org.springframework.ui.Model;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;   // <-- Add this!
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookoryteam.bookory.model.Book;
import com.bookoryteam.bookory.model.Seller;
import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.service.AuthorService;
import com.bookoryteam.bookory.service.BookService;
import com.bookoryteam.bookory.service.CategoryService;
import com.bookoryteam.bookory.service.PublisherService;
import com.bookoryteam.bookory.service.SellerService;

@Controller
@RequestMapping("/admin/book")
public class BookAdminController {

    private final BookService bookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final SellerService sellerService;

    public BookAdminController(BookService bookService, PublisherService publisherService,
            AuthorService authorService, CategoryService categoryService,
            SellerService sellerService) {
			this.bookService = bookService;
			this.publisherService = publisherService;
			this.authorService = authorService;
			this.categoryService = categoryService;
			this.sellerService = sellerService;
}

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("currentPageTitle", "Quản Lý Sách");
        return "admin/book";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Book book = new Book();
        book.setDeleted(false); 

        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Thêm sách mới");
        model.addAttribute("isEdit", false);


        model.addAttribute("publishers", publisherService.findAll());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("sellers", sellerService.findAll());


        return "book/create_edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
          
            Optional<Book> optionalBook = bookService.findById(id);
            if (!optionalBook.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách với ID: " + id);
                return "redirect:/admin/book";
            }

            Book book = optionalBook.get();
            if (book.getSeller() == null) {
                book.setSeller(new Seller());
            }
            if (book.getSeller().getUser() == null) {
                book.getSeller().setUser(new User());
            }
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Chỉnh sửa sách");
            model.addAttribute("isEdit", true);

           
            model.addAttribute("publishers", publisherService.findAll());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("sellers", sellerService.findByDeletedFalse());

            return "book/create_edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải thông tin sách: " + e.getMessage());
            return "redirect:/admin/book";
        }
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book,
                           @RequestParam("sellerId") Long sellerId,
                           RedirectAttributes redirectAttributes) {
        try {
            if (book.getId() == null) {
                book.setDeleted(false);
            }

            Optional<Seller> optionalSeller = sellerService.findById(sellerId);
            if (!optionalSeller.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Seller không tồn tại!");
                if (book.getId() == null) {
                    return "redirect:/admin/book/new";
                } else {
                    return "redirect:/admin/book/edit/" + book.getId();
                }
            }
            book.setSeller(optionalSeller.get());


            Book savedBook = bookService.save(book);

            if (book.getId() == null) {
                redirectAttributes.addFlashAttribute("successMessage",
                    "Thêm sách '" + savedBook.getTitle() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage",
                    "Cập nhật sách '" + savedBook.getTitle() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Lỗi khi lưu sách: " + e.getMessage());
        }

        return "redirect:/admin/book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    
        try {
            Optional<Book> optionalBook = bookService.findById(id);
            if (!optionalBook.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách với ID: " + id);
                return "redirect:/admin/book";
            }

            Book book = optionalBook.get();
            book.setDeleted(true);
            bookService.save(book);

            redirectAttributes.addFlashAttribute("successMessage",
                "Đã ẩn sách '" + book.getTitle() + "' thành công!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Lỗi khi ẩn sách: " + e.getMessage());
        }

        return "redirect:/admin/book";
    }
}