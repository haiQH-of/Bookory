package com.bookoryteam.bookory.controller.crud;

import com.bookoryteam.bookory.model.Book;
import com.bookoryteam.bookory.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book") // Base URL là /book (số ít)
public class BookController1 {

    private final BookService bookService;

    public BookController1(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Hiển thị danh sách tất cả sách.
     * GET /book
     *
     * @param model Đối tượng Model để truyền dữ liệu tới view.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping
    public String listBook(Model model) {
        // TODO: Lấy danh sách sách từ BookService và thêm vào model
        // TODO: Đảm bảo có file 'src/main/resources/templates/book/list.html'
    	 try {
             model.addAttribute("books", bookService.findAll());
             return "book/list1"; // Fixed: changed from list1 to list
         } catch (Exception e) {
             model.addAttribute("errorMessage", "Lỗi khi tải danh sách sách: " + e.getMessage());
             return "book/list1";
         }
    }

    /**
     * Hiển thị form để thêm sách mới.
     * GET /book/new
     *
     * @param model Đối tượng Model để truyền một đối tượng Book rỗng tới form.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // TODO: Tạo một đối tượng Book rỗng và thêm vào model để bind với form
        // TODO: Đảm bảo có file 'src/main/resources/templates/book/create_edit.html'
        model.addAttribute("book", new Book());
        return "book/new";
    }

    /**
     * Xử lý yêu cầu POST để lưu sách (mới hoặc cập nhật).
     * POST /book/save
     *
     * @param book Đối tượng Book được bind từ dữ liệu form.
     * @param redirectAttributes Dùng để thêm thông báo flash sau khi redirect.
     * @return Redirect tới trang danh sách sách.
     */
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        // TODO: Gọi BookService để lưu/cập nhật sách.
        // TODO: Xử lý thành công/thất bại và thêm thông báo.
    	try {
            // Gọi BookService để lưu/cập nhật sách
            Book savedBook = bookService.save(book);
            
            if (book.getId() == null) {
                // Thêm mới
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Sách '" + savedBook.getTitle() + "' đã được thêm thành công!");
            } else {
                // Cập nhật
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Sách '" + savedBook.getTitle() + "' đã được cập nhật thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Lỗi khi lưu sách: " + e.getMessage());
        }
        
        return "redirect:/book";
    }

    /**
     * Hiển thị form để chỉnh sửa thông tin sách.
     * GET /book/edit/{id}
     *
     * @param id ID của sách cần chỉnh sửa.
     * @param model Đối tượng Model để truyền đối tượng Book hiện có tới form.
     * @param redirectAttributes Dùng để thêm thông báo nếu không tìm thấy sách.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Lấy sách theo ID từ BookService
            return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "book/create_edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", 
                        "Không tìm thấy sách với ID: " + id);
                    return "redirect:/book";
                });
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Lỗi khi tải thông tin sách: " + e.getMessage());
            return "redirect:/book";
        }
    }

    /**
     * Xóa một sách khỏi hệ thống.
     * GET /book/delete/{id}
     *
     * @param id ID của sách cần xóa.
     * @param redirectAttributes Dùng để thêm thông báo flash sau khi xóa.
     * @return Redirect tới trang danh sách sách.
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    	bookService.deleteById(id); 
        // TODO: Gọi BookService để xóa sách.
        // TODO: Xử lý thành công/thất bại và thêm thông báo.
        return "redirect:/book"; // Redirect tới /book (số ít)
    }
}