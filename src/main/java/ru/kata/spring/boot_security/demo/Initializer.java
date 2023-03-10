package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Initializer {
    private final UserService userService;
    private final RoleService roleService;

    public Initializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    @Transactional
    public void init() {
        User user = new User("user@mail.ru", "user");
        User admin = new User("admin@mail.ru", "admin");
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userService.saveUser(user);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);
        userService.saveUser(admin);
    }
}
