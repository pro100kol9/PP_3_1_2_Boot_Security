package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/admin/admin_page";
    }

    @GetMapping("/redactor/{id}")
    public String getAdminRedactor(Model user, @PathVariable("id") Long id) {
        user.addAttribute("user",userService.getUserById(id).get());
        return "/admin/admin_redactor";
    }

    @PatchMapping("/redactor/{id}")
    public String patchAdminRedactor(@ModelAttribute ("user") User user, @PathVariable("id") Long id) {
        userService.adminRedactor(user, id);
        return "redirect:/admin/page";
    }

    @DeleteMapping("/delete/{id}")
    public String adminDelete (@PathVariable ("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/page";
    }
}
