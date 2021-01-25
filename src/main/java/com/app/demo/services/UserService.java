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
        if(userRepository.findByTel(user.getTel()) != null){
            throw new UsernameAlreadyExistException("User already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByLogin(String tel){
        User user=userRepository.findByTel(tel);
        if(user == null) throw new UserNotFoundException("User not found with tel : "+tel);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
