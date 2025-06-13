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

import com.bookoryteam.bookory.model.Category;
import com.bookoryteam.bookory.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {
    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("currentPageTitle", "Quản Lý Thể Loại");
        return "admin/category"; 
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Thêm thể loại mới");
        model.addAttribute("isEdit", false);
        return "category/create_edit";  
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> optionalCategory = categoryService.findById(id);
            if (!optionalCategory.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thể loại với ID: " + id);
                return "redirect:/admin/category";
            }

            Category category = optionalCategory.get();
            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Chỉnh sửa thể loại");
            model.addAttribute("isEdit", true);
            return "category/create_edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải thông tin thể loại: " + e.getMessage());
            return "redirect:/admin/category";
        }
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        try {
  
            if (category.getId() == null) {
                category.setDeleted(false);
            }

            // Validate tên thể loại
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Tên thể loại không được để trống.");
                if (category.getId() == null) {
                    return "redirect:/admin/category/new";
                } else {
                    return "redirect:/admin/category/edit/" + category.getId();
                }
            }

            Category savedCategory = categoryService.save(category);

            if (category.getId() == null) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Thêm thể loại '" + savedCategory.getName() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Cập nhật thể loại '" + savedCategory.getName() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu thể loại: " + e.getMessage());
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> optionalCategory = categoryService.findById(id);
            if (!optionalCategory.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thể loại với ID: " + id);
                return "redirect:/admin/category";
            }

            Category category = optionalCategory.get();
            category.setDeleted(true);
            categoryService.save(category);

            redirectAttributes.addFlashAttribute("successMessage", "Đã ẩn thể loại '" + category.getName() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ẩn thể loại: " + e.getMessage());
        }

        return "redirect:/admin/category";
    }
}
