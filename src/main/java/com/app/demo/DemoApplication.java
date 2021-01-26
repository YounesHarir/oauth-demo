package com.app.demo;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            System.out.println("creating");
            User user=new User();
            user.setPassword(passwordEncoder.encode("123"));
            user.setUsername("123456789");

            List<Role> roles=new ArrayList<>();
            Role role=new Role();
            role.setName("ROLE_AGENT");
            role.setDescription("This is agent");
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        };
    }

}
