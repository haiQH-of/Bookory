package com.bookoryteam.bookory.controller.admin;

import com.bookoryteam.bookory.model.Order;
import com.bookoryteam.bookory.model.OrderItem;
import com.bookoryteam.bookory.service.OrderService;
import com.bookoryteam.bookory.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin/order")
public class OrderAdminController {
	
    private final OrderService orderService;
    private final UserService userService;

    public OrderAdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.findByDeletedFalse();
        
        long totalOrders = orders.size();
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long pendingOrders = countOrdersByStatus(orders, "PENDING");
        long deliveredOrders = countOrdersByStatus(orders, "DELIVERED");
        
        model.addAttribute("orders", orders);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("currentPageTitle", "Quản Lý Đơn Hàng");
        
        return "admin/order";
    }

  
    @GetMapping("/deleted")
    public String listDeletedOrders(Model model) {
        List<Order> deletedOrders = orderService.findByDeletedTrue();
        
        model.addAttribute("orders", deletedOrders);
        model.addAttribute("isDeletedList", true);
        model.addAttribute("currentPageTitle", "Đơn Hàng Đã Xóa");
        
        return "admin/order-deleted";
    }

   
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        return orderService.findById(id)
                .map(order -> {
                    order.setDeleted(true);
                    orderService.save(order);
                    redirectAttributes.addFlashAttribute("success", "Đơn hàng đã được xóa thành công!");
                    return "redirect:/admin/order";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng!");
                    return "redirect:/admin/order";
                });
    }
    
   
    @GetMapping("/restore/{id}")
    public String restoreOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        return orderService.findById(id)
                .map(order -> {
                    order.setDeleted(false);
                    orderService.save(order);
                    redirectAttributes.addFlashAttribute("success", "Đơn hàng đã được khôi phục thành công!");
                    return "redirect:/admin/order/deleted";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng!");
                    return "redirect:/admin/order/deleted";
                });
    }
    
   
    @GetMapping("/hard-delete/{id}")
    public String hardDeleteOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        return orderService.findById(id)
                .map(order -> {
                    if (order.getDeleted()) {
                        orderService.deleteById(id);
                        redirectAttributes.addFlashAttribute("success", "Đơn hàng đã được xóa vĩnh viễn khỏi hệ thống!");
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Chỉ có thể xóa cứng đơn hàng đã bị xóa mềm!");
                    }
                    return "redirect:/admin/order/deleted";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng!");
                    return "redirect:/admin/order/deleted";
                });
    }
    
    @GetMapping("/view/{id}")
    public String viewOrderItems(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("orderItems", order.getOrderItems());
                    model.addAttribute("currentPageTitle", "Chi Tiết Đơn Hàng #" + id);
                    return "admin/order-item";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng!");
                    return "redirect:/admin/order";
                });
    }
    
    private long countOrdersByStatus(List<Order> orders, String status) {
        return orders.stream()
                .filter(order -> status.equals(order.getStatus()))
                .count();
    }
}