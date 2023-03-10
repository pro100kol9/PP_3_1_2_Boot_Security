package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;

@Controller
@RequestMapping("/guest")
public class GuestController {
    private final UserService userService;
    private final RoleService roleService;

    public GuestController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login() {
        return "/guest/login";
    }

    @GetMapping("/registration")
    public String registrationGet(@ModelAttribute("newuser") User user) {
        return "/guest/registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("newuser") User user) {
        user.setRoles(Arrays.asList(roleService.findByRoleName("ROLE_USER")));
        userService.saveUser(user);
        return "redirect:/guest/login";
    }
}
