package com.app.demo;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.repository.RoleRepository;
import com.app.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    public void createUser(){
        System.out.println("creating user");
        User user=new User();
        user.setPassword(passwordEncoder.encode("123"));
        user.setTel("123456789");

        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setName("ROLE_ADMIN");
        role.setDescription("admin");
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

}
