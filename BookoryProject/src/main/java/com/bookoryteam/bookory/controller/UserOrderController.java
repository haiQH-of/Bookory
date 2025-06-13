package com.bookoryteam.bookory.controller;

import com.bookoryteam.bookory.model.Order;
import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class UserOrderController {
    
    private final OrderService orderService;
    
    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/user/orders")
    public String viewUserOrders(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            session.setAttribute("redirectUrl", "/user/orders");
            redirectAttributes.addFlashAttribute("info", "Vui lòng đăng nhập để xem đơn hàng");
            return "redirect:/login";
        }
        
        List<Order> userOrders = orderService.findByUserId(currentUser.getId());
        model.addAttribute("orders", userOrders);
        return "public/user-orders";
    }
    
    @GetMapping("/user/orders/{id}")
    public String viewOrderDetail(@PathVariable("id") Long id, Model model, HttpSession session, 
                                RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            session.setAttribute("redirectUrl", "/user/orders/" + id);
            redirectAttributes.addFlashAttribute("info", "Vui lòng đăng nhập để xem chi tiết đơn hàng");
            return "redirect:/login";
        }
        
        return orderService.findById(id)
                .map(order -> {
                    // Kiểm tra xem đơn hàng có thuộc về người dùng hiện tại không
                    if (!order.getUser().getId().equals(currentUser.getId())) {
                        redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xem đơn hàng này");
                        return "redirect:/user/orders";
                    }
                    
                    model.addAttribute("order", order);
                    model.addAttribute("orderItems", order.getOrderItems());
                    return "public/user-order-detail";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng");
                    return "redirect:/user/orders";
                });
    }
} 