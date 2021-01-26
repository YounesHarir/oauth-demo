package com.app.demo.controller;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.exception.UsernameAlreadyExistException;
import com.app.demo.repository.RoleRepository;
import com.app.demo.repository.UserRepository;
import com.app.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/get/{username}")
    public User findUserByLogin(@PathVariable String username){
        return userService.findUserByLogin(username);
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.findAll();
    }
}