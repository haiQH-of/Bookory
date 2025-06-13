package com.bookoryteam.bookory.controller.crud;

import com.bookoryteam.bookory.model.Author;
import com.bookoryteam.bookory.service.AuthorService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/author") 
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Hiển thị danh sách tất cả tác giả.
     * GET /author
     *
     * @param model Đối tượng Model để truyền dữ liệu tới view.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping
    public String listAuthor(Model model) {
        // TODO: Lấy danh sách tác giả từ AuthorService và thêm vào model
        // TODO: Đảm bảo có file 'src/main/resources/templates/author/list.html'
    	try {
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("pageTitle", "Danh sách tác giả");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể tải danh sách tác giả: " + e.getMessage());
        }
        return "author/list"; // Tên thư mục view là author (số ít)    
        }

    /**
     * Hiển thị form để thêm tác giả mới.
     * GET /author/new
     *
     * @param model Đối tượng Model để truyền một đối tượng Author rỗng tới form.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // TODO: Tạo một đối tượng Author rỗng và thêm vào model để bind với form
        // TODO: Đảm bảo có file 'src/main/resources/templates/author/create_edit.html'
    	 Author author = new Author();
         model.addAttribute("author", author);
         model.addAttribute("pageTitle", "Thêm tác giả mới");
         model.addAttribute("isEdit", false);
         return "author/create_edit"; // Tên thư mục view là author (số ít) 
         }

    /**
     * Xử lý yêu cầu POST để lưu tác giả (mới hoặc cập nhật).
     * POST /author/save
     *
     * @param author Đối tượng Author được bind từ dữ liệu form.
     * @param redirectAttributes Dùng để thêm thông báo flash sau khi redirect.
     * @return Redirect tới trang danh sách tác giả.
     */
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        // TODO: Gọi AuthorService để lưu/cập nhật tác giả.
        // TODO: Xử lý thành công/thất bại và thêm thông báo.
    	try {
            // Kiểm tra validation cơ bản
            if (author.getName() == null || author.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Tên tác giả không được để trống.");
                return "redirect:/author/new";
            }
            
            Author savedAuthor = authorService.save(author);
            
            if (author.getId() == null) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Thêm tác giả '" + savedAuthor.getName() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Cập nhật tác giả '" + savedAuthor.getName() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Lỗi khi lưu tác giả: " + e.getMessage());
        }
        
        return "redirect:/author"; // Redirect tới /author (số ít)   
        }

    /**
     * Hiển thị form để chỉnh sửa thông tin tác giả.
     * GET /author/edit/{id}
     *
     * @param id ID của tác giả cần chỉnh sửa.
     * @param model Đối tượng Model để truyền đối tượng Author hiện có tới form.
     * @param redirectAttributes Dùng để thêm thông báo nếu không tìm thấy tác giả.
     * @return Tên của Thymeleaf template.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        // TODO: Lấy tác giả theo ID từ AuthorService.
        // TODO: Nếu tìm thấy, thêm vào model. Nếu không, thêm thông báo lỗi và redirect.
    	try {
            Optional<Author> authorOptional = authorService.findById(id);
            
            if (authorOptional.isPresent()) {
                Author author = authorOptional.get();
                model.addAttribute("author", author);
                model.addAttribute("pageTitle", "Chỉnh sửa tác giả: " + author.getName());
                model.addAttribute("isEdit", true);
                return "author/create_edit"; // Tên thư mục view là author (số ít)
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Không tìm thấy tác giả với ID: " + id);
                return "redirect:/author";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Lỗi khi tải thông tin tác giả: " + e.getMessage());
            return "redirect:/author";
            }
    }

    /**
     * Xóa một tác giả khỏi hệ thống.
     * GET /author/delete/{id}
     *
     * @param id ID của tác giả cần xóa.
     * @param redirectAttributes Dùng để thêm thông báo flash sau khi xóa.
     * @return Redirect tới trang danh sách tác giả.
     */
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Author> authorOptional = authorService.findById(id);

            if (authorOptional.isPresent()) {
                Author author = authorOptional.get();
                String authorName = author.getName();

                // Gọi service xóa tác giả (bạn có thể xóa vĩnh viễn hoặc đánh dấu deleted)
                authorService.deleteById(id);

                redirectAttributes.addFlashAttribute("successMessage", 
                    "Xóa tác giả '" + authorName + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Không tìm thấy tác giả với ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Lỗi khi xóa tác giả: " + e.getMessage());
        }
        return "redirect:/author"; // Redirect về trang danh sách tác giả
    }

         @GetMapping("/view/{id}")
         public String viewAuthor(@PathVariable("id") Long id, Model model, 
                                 RedirectAttributes redirectAttributes) {
             try {
                 Optional<Author> authorOptional = authorService.findById(id);
                 
                 if (authorOptional.isPresent()) {
                     Author author = authorOptional.get();
                     model.addAttribute("author", author);
                     model.addAttribute("pageTitle", "Chi tiết tác giả: " + author.getName());
                     
                     // Có thể thêm danh sách sách của tác giả này
                     // List<Book> books = bookService.findByAuthorId(id);
                     // model.addAttribute("books", books);
                     
                     return "author/view";
                 } else {
                     redirectAttributes.addFlashAttribute("errorMessage", 
                         "Không tìm thấy tác giả với ID: " + id);
                     return "redirect:/author";
                 }
             } catch (Exception e) {
                 redirectAttributes.addFlashAttribute("errorMessage", 
                     "Lỗi khi tải thông tin tác giả: " + e.getMessage());
                 return "redirect:/author";
             }
         
         
    }
    }

    
