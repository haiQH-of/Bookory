package com.bookoryteam.bookory.controller.admin;

import com.bookoryteam.bookory.model.Seller;
import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.service.SellerService;
import com.bookoryteam.bookory.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/seller")
public class SellerAdminController {
	
    private final SellerService sellerService;
    private final UserService userService;

    public SellerAdminController(SellerService sellerService, UserService userService) {
        this.sellerService = sellerService;
        this.userService = userService;
    }

    @GetMapping
    public String listSellers(Model model) {
        List<Seller> sellers = sellerService.findAll();
        model.addAttribute("sellers", sellers);
        model.addAttribute("currentPageTitle", "Quản Lý Người Bán");
        return "admin/seller";
    }

    @GetMapping("/delete/{userId}")
    public String deleteSeller(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        return "redirect:/admin/seller";
    }
}