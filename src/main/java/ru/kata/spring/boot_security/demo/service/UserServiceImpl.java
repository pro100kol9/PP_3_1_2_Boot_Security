package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userDao) {
        this.userRepository = userDao;
    }

    @Transactional(readOnly = true)
    public User findByEmailWithRoles(String email) {
        return userRepository.findByEmailWithRoles(email);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void adminRedactor(User user, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User editUser = optionalUser.get();
            editUser.setId(user.getId());
            editUser.setFirstName(user.getFirstName());
            editUser.setLastName(user.getLastName());
            editUser.setEmail(user.getEmail());
            editUser.setPassword(user.getPassword());
            editUser.setRoles(user.getRoles());
            userRepository.save(user);
        }
    }

    @Transactional
    public void userRedactor(User user, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User editUser = optionalUser.get();
            editUser.setFirstName(user.getFirstName());
            editUser.setLastName(user.getLastName());
            editUser.setEmail(user.getEmail());
            editUser.setPassword(user.getPassword());
            editUser.setRoles(user.getRoles());
            userRepository.save(user);
        }
    }
    
    @Transactional
    public void delete(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmailWithRoles(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

}
