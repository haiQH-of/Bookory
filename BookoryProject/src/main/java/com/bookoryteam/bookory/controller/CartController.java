package com.bookoryteam.bookory.controller;

import com.bookoryteam.bookory.model.Book;
import com.bookoryteam.bookory.model.Order;
import com.bookoryteam.bookory.model.OrderItem;
import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.service.BookService;
import com.bookoryteam.bookory.service.OrderService;
import com.bookoryteam.bookory.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    private static final String CART_COOKIE_NAME = "bookory_cart";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 ngày

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    public CartController(BookService bookService, UserService userService, OrderService orderService) {
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String viewCart(Model model, @CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie) {
        Map<Long, Integer> cart = getCartFromCookie(cartCookie);
        List<Map<String, Object>> cartItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Book book = bookService.findById(entry.getKey()).orElse(null);
            if (book != null) {
                Map<String, Object> item = new HashMap<>();
                item.put("book", book);
                item.put("quantity", entry.getValue());
                BigDecimal subtotal = book.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
                item.put("subtotal", subtotal);
                
                cartItems.add(item);
                totalPrice = totalPrice.add(subtotal);
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "public/cart";
    }

    @GetMapping("/add")
    public String addToCartGet(@RequestParam("bookId") Long bookId, 
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                            @RequestParam(value = "redirectToHome", defaultValue = "false") boolean redirectToHome,
                            @CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        
        if (bookService.findById(bookId).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sách không tồn tại");
            return redirectToHome ? "redirect:/" : "redirect:/cart";
        }
        
        updateCart(bookId, quantity, cartCookie, response, true);
        
        // Kiểm tra nếu là request Ajax (từ JavaScript)
        String requestedWith = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestedWith)) {
            // Trả về phản hồi JSON thay vì chuyển hướng trang
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                response.getWriter().write("{\"success\": true, \"message\": \"Đã thêm sách vào giỏ hàng\"}");
                return null; // Trả về null để không render view
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        redirectAttributes.addFlashAttribute("success", "Đã thêm sách vào giỏ hàng");
        return redirectToHome ? "redirect:/" : "redirect:/cart";
    }

    @PostMapping("/add")
    public String addToCartPost(@RequestParam("bookId") Long bookId, 
                             @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                             @CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie,
                             HttpServletResponse response,
                             RedirectAttributes redirectAttributes) {
        
        if (bookService.findById(bookId).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sách không tồn tại");
            return "redirect:/cart";
        }
        
        updateCart(bookId, quantity, cartCookie, response, true);
        redirectAttributes.addFlashAttribute("success", "Đã thêm sách vào giỏ hàng");
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("bookId") Long bookId,
                           @RequestParam("quantity") Integer quantity,
                           @CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie,
                           HttpServletResponse response,
                           RedirectAttributes redirectAttributes) {
        
        updateCart(bookId, quantity, cartCookie, response, false);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật giỏ hàng");
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("bookId") Long bookId,
                               @CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {
        
        Map<Long, Integer> cart = getCartFromCookie(cartCookie);
        cart.remove(bookId);
        saveCartToCookie(cart, response);
        
        redirectAttributes.addFlashAttribute("success", "Đã xóa sách khỏi giỏ hàng");
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@CookieValue(name = CART_COOKIE_NAME, required = false) String cartCookie,
                         HttpSession session,
                         HttpServletResponse response,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            // NOTE: [Đã hoàn thành - Khang]
            // - Đã xử lý chuyển hướng đúng khi người dùng chưa đăng nhập
            // - Lưu URL giỏ hàng vào session để chuyển lại sau khi đăng nhập
            // - Đã kết hợp xử lý với AuthController để tạo luồng thanh toán liền mạch
            session.setAttribute("redirectUrl", "/cart");
            redirectAttributes.addFlashAttribute("info", "Vui lòng đăng nhập để thanh toán");
            return "redirect:/login";
        }
        
        Map<Long, Integer> cart = getCartFromCookie(cartCookie);
        if (cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống, không thể thanh toán");
            return "redirect:/cart";
        }
        
        // Tạo đơn hàng mới
        Order order = createOrder(currentUser, cart);
        orderService.save(order);
        
        // Lưu thông tin đơn hàng vào model để hiển thị trang xác nhận
        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems());
        model.addAttribute("totalAmount", order.getTotalAmount());
        
        clearCartCookie(response);
        return "public/order-confirmation";
    }
    
    // Helper method để cập nhật giỏ hàng
    private void updateCart(Long bookId, Integer quantity, String cartCookie, 
                          HttpServletResponse response, boolean isAdd) {
        Map<Long, Integer> cart = getCartFromCookie(cartCookie);
        
        if (quantity <= 0 && !isAdd) {
            cart.remove(bookId);
        } else if (isAdd && cart.containsKey(bookId)) {
            cart.put(bookId, cart.get(bookId) + quantity);
        } else {
            cart.put(bookId, quantity);
        }
        
        saveCartToCookie(cart, response);
    }
    
    // Helper method để tạo đơn hàng
    private Order createOrder(User user, Map<Long, Integer> cart) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setPaymentStatus("UNPAID");
        order.setOrderDate(OffsetDateTime.now());
        order.setShippingAddress("Địa chỉ mặc định");
        order.setPaymentMethod("COD");
        order.setDeleted(false);
        order.setTotalAmount(BigDecimal.ZERO); // Khởi tạo giá trị ban đầu
        
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            bookService.findById(entry.getKey()).ifPresent(book -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setBook(book);
                orderItem.setQuantity(entry.getValue());
                orderItem.setPriceAtPurchase(book.getPrice());
                orderItem.setOrder(order);
                
                orderItems.add(orderItem);
                
                // Cập nhật tổng tiền
                BigDecimal itemTotal = book.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
                order.setTotalAmount(order.getTotalAmount().add(itemTotal));
            });
        }
        
        order.setOrderItems(orderItems);
        return order;
    }
    
    // Helper method để lấy giỏ hàng từ cookie
    private Map<Long, Integer> getCartFromCookie(String cartCookie) {
        Map<Long, Integer> cart = new HashMap<>();
        
        if (cartCookie != null && !cartCookie.isEmpty()) {
            try {
                String decodedCookie = URLDecoder.decode(cartCookie, StandardCharsets.UTF_8.name());
                Arrays.stream(decodedCookie.split("\\|"))
                    .filter(item -> item.contains(":"))
                    .forEach(item -> {
                        String[] parts = item.split(":");
                        cart.put(Long.parseLong(parts[0]), Integer.parseInt(parts[1]));
                    });
            } catch (Exception e) {
                System.err.println("Lỗi khi parse cookie giỏ hàng: " + e.getMessage());
            }
        }
        
        return cart;
    }
    
    // Helper method để lưu giỏ hàng vào cookie
    private void saveCartToCookie(Map<Long, Integer> cart, HttpServletResponse response) {
        if (cart.isEmpty()) {
            clearCartCookie(response);
            return;
        }
        
        try {
            String cookieValue = cart.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .reduce((s1, s2) -> s1 + "|" + s2)
                .orElse("");
                
            String encodedValue = URLEncoder.encode(cookieValue, StandardCharsets.UTF_8.name());
            Cookie cookie = new Cookie(CART_COOKIE_NAME, encodedValue);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE);
            response.addCookie(cookie);
        } catch (Exception e) {
            System.err.println("Lỗi khi encode cookie giỏ hàng: " + e.getMessage());
        }
    }
    
    // Helper method để xóa cookie giỏ hàng
    private void clearCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
} 