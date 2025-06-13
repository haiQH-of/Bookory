package com.bookoryteam.bookory.controller.admin;

import java.util.List;
import java.util.Optional;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookoryteam.bookory.model.Author;
import com.bookoryteam.bookory.service.AuthorService;

@Controller
@RequestMapping("/admin/author")
public class AuthorAdminController {
    
    private final AuthorService authorService;

    public AuthorAdminController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("currentPageTitle", "Quản Lý Tác Giả");
        return "admin/author";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
             Author author = new Author();
        model.addAttribute("author", author);
        model.addAttribute("pageTitle", "Thêm tác giả mới");
        model.addAttribute("isEdit", false);
        return "author/create_edit";
    }

   
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Author> optionalAuthor = authorService.findById(id);
            if (!optionalAuthor.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tác giả với ID: " + id);
                return "redirect:/admin/author";
            }

            Author author = optionalAuthor.get();
            model.addAttribute("author", author);
            model.addAttribute("pageTitle", "Chỉnh sửa tác giả");
            model.addAttribute("isEdit", true);
            return "author/create_edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải thông tin tác giả: " + e.getMessage());
            return "redirect:/admin/author";
        }
    }

    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
 
        try {
          
            if (author.getId() == null) {
                author.setDeleted(false);
            }

            if (author.getName() == null || author.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Tên tác giả không được để trống.");
                if (author.getId() == null) {
                    return "redirect:/admin/author/new";
                } else {
                    return "redirect:/admin/author/edit/" + author.getId();
                }
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

        return "redirect:/admin/author";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    
        try {
            Optional<Author> optionalAuthor = authorService.findById(id);
            if (!optionalAuthor.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tác giả với ID: " + id);
                return "redirect:/admin/author";
            }

            Author author = optionalAuthor.get();           
            author.setDeleted(true);
            authorService.save(author);

            redirectAttributes.addFlashAttribute("successMessage",
                "Đã ẩn tác giả '" + author.getName() + "' thành công!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Lỗi khi ẩn tác giả: " + e.getMessage());
        }

        return "redirect:/admin/author";
    }
}