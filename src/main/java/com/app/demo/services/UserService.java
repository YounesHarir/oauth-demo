package com.app.demo.services;

import com.app.demo.entity.User;
import com.app.demo.exception.UserNotFoundException;
import com.app.demo.exception.UsernameAlreadyExistException;
import com.app.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new UsernameAlreadyExistException("User already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByLogin(String username){
        User user=userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException("User not found with username : "+username);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
