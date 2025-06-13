package com.bookoryteam.bookory.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookoryteam.bookory.model.Publisher;
import com.bookoryteam.bookory.service.PublisherService;

@Controller
@RequestMapping("/admin/publisher")
public class PublisherAdminController {
    private final PublisherService publisherService;

    public PublisherAdminController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String listPublishers(Model model) {
        List<Publisher> publishers = publisherService.findAll();
        model.addAttribute("publishers", publishers);
        model.addAttribute("currentPageTitle", "Quản Lý Nhà Xuất Bản");
        return "admin/publisher";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        model.addAttribute("pageTitle", "Thêm nhà xuất bản mới");
        model.addAttribute("isEdit", false);
        return "publisher/create_edit";
    }

    @PostMapping("/save")
    public String savePublisher(@ModelAttribute("publisher") Publisher publisher, RedirectAttributes redirectAttributes) {
        try {

                if (publisher.getId() == null) {
                	publisher.setDeleted(false);
                }
            if (publisher.getName() == null || publisher.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Tên nhà xuất bản không được để trống.");
                return "redirect:/admin/publisher/new";
            }

            Publisher savedPublisher = publisherService.save(publisher);

            if (publisher.getId() == null) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Thêm nhà xuất bản '" + savedPublisher.getName() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Cập nhật nhà xuất bản '" + savedPublisher.getName() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi khi lưu nhà xuất bản: " + e.getMessage());
        }

        return "redirect:/admin/publisher";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Publisher> publisherOptional = publisherService.findById(id);
            if (publisherOptional.isPresent()) {
                Publisher publisher = publisherOptional.get();
                model.addAttribute("publisher", publisher);
                model.addAttribute("pageTitle", "Chỉnh sửa nhà xuất bản: " + publisher.getName());
                model.addAttribute("isEdit", true);
                return "publisher/create_edit";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Không tìm thấy nhà xuất bản với ID: " + id);
                return "redirect:/admin/publisher";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi khi tải thông tin nhà xuất bản: " + e.getMessage());
            return "redirect:/admin/publisher";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Publisher> publisherOptional = publisherService.findById(id);
            if (publisherOptional.isPresent()) {
                Publisher publisher = publisherOptional.get();

                // Xóa mềm: set deleted = true
                publisher.setDeleted(true);
                publisherService.save(publisher);

                redirectAttributes.addFlashAttribute("successMessage",
                        "Đã ẩn nhà xuất bản '" + publisher.getName() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Không tìm thấy nhà xuất bản với ID: " + id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Lỗi khi xóa nhà xuất bản: " + e.getMessage());
        }
        return "redirect:/admin/publisher";
    }
}
