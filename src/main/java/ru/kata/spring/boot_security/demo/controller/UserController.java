package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String userPage(Model user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        user.addAttribute("user", userService.getUserById(userDetails.getId()).get());
        return "/user/user_page";
    }

    @GetMapping("/redactor/{id}")
    public String getUserRedactor(Model user, @PathVariable("id") Long id) {
        user.addAttribute("user",userService.getUserById(id).get());
        return "/user/user_redactor";
    }

    @PatchMapping("/redactor/{id}")
    public String patchUserRedactor(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.userRedactor(user, id);
        return "redirect:/user/page";
    }

    @DeleteMapping("/delete/{id}")
    public String adminDelete (@PathVariable ("id") Long id) {
        userService.delete(id);
        return "redirect:/guest/login";
    }
}
