package com.bookoryteam.bookory.controller;

import com.bookoryteam.bookory.request.LoginRequest;  
import com.bookoryteam.bookory.request.RegisterRequest; 
import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService; 

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Thêm đối tượng request vào model để form có thể bind dữ liệu
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) {

        // Kiểm tra lỗi validation từ DTO
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            // Xác thực người dùng thông qua AuthService
            boolean isAuthenticated = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

            if (isAuthenticated) {
                // Đăng nhập thành công: Lấy thông tin người dùng và lưu vào session
                Optional<User> loggedInUserOptional = authService.findUserByUsername(loginRequest.getUsername());
                
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    request.getSession().setAttribute("loggedInUser", loggedInUser);
                    request.getSession().setAttribute("userId", loggedInUser.getId());
                    request.getSession().setAttribute("username", loggedInUser.getUsername());
                    
                    // NOTE: [Đã hoàn thành - KhangY]
                    // - Đã cải thiện luồng xác thực ngưi dùng 
                    // - Đã xử lý chuyển hướng sau đăng nhập để hỗ trợ thanh toán giỏ hàng
                    // - Đã thêm thông báo thành công và lưu trữ thông tin người dùng vào session
                    String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
                    if (redirectUrl != null) {
                        request.getSession().removeAttribute("redirectUrl");
                        return "redirect:" + redirectUrl;
                    }
                }
                
                redirectAttributes.addFlashAttribute("successMessage", "Đăng nhập thành công!");
                
                if(loggedInUserOptional.get().getRole().equals("ADMIN")) {
                	return "redirect:/admin/dashboard"; // Chuyển hướng về trang quản trị
                }
                return "redirect:/"; // Chuyển hướng về trang chính
            } else {
                // Đăng nhập thất bại
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng.");
                return "auth/login";
            }
        } catch (Exception e) {
            // Xử lý lỗi hệ thống
            model.addAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng nhập. Vui lòng thử lại.");
            e.printStackTrace(); 
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Thêm đối tượng request vào model để form có thể bind dữ liệu
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        // Kiểm tra lỗi validation từ DTO
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registerRequest", "Xác nhận mật khẩu không khớp.");
            return "auth/register";
        }

        try {
            // Kiểm tra tên đăng nhập đã tồn tại
            if (authService.isUsernameTaken(registerRequest.getUsername())) {
                bindingResult.rejectValue("username", "error.registerRequest", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
                return "auth/register";
            }

            // Đăng ký người dùng thông qua AuthService
            authService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
            
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập
        } catch (IllegalStateException e) {
            // Bắt lỗi khi tên đăng nhập đã tồn tại (nếu AuthService ném lỗi này)
            bindingResult.rejectValue("username", "error.registerRequest", e.getMessage());
            return "auth/register";
        } catch (Exception e) {
            // Xử lý lỗi hệ thống
            model.addAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng ký. Vui lòng thử lại.");
            e.printStackTrace();
            return "auth/register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password";
    }

    @GetMapping("/logout") 
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false); 
        if (session != null) {
            session.invalidate(); // Hủy bỏ session
        }
        redirectAttributes.addFlashAttribute("successMessage", "Bạn đã đăng xuất thành công.");
        return "redirect:/login"; // Chuyển hướng về trang đăng nhập
    }
}