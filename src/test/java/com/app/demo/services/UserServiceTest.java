package com.app.demo.services;

import static org.mockito.BDDMockito.given;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.exception.UserNotFoundException;
import com.app.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private UserService userService ;
    @MockBean
    private UserRepository userRepository ;

    @Test(expected = UserNotFoundException.class)
    public void saveUserTestCasException() {
        User user = null;
        given(userRepository.save(user))
                .willThrow(new UserNotFoundException("userNot Found"));
        userService.save(user);
    }
    @Test
    public void saveUserTest() {
        List<Role> roles = null ;
        User user = new User();
        user.setId(3L);
        user.setUsername("younes");
        user.setPassword("younes123");
        user.setRoles(roles);
        given(userRepository.save(user)).willReturn(user);
        userService.save(user);
    }
    @Test(expected = UserNotFoundException.class)
    public void NotFoundUserTest() {
        Optional<User> optional = null;
        given(userRepository.findById(anyLong())).willReturn(optional);
        userService.findUserByLogin(any());

    }
    @Test
    public void FindUserTest() {
        List<Role> roles = null ;
        User user = new User();
        user.setId(3L);
        user.setUsername("younes");
        user.setPassword("younes123");
        user.setRoles(roles);
        Optional<User> optional = Optional.of(user);
        given(userRepository.findById(1L)).willReturn(optional);

        assertEquals(userService.findUserByLogin(user.getUsername()), userRepository.findByUsername("younes"));
        Assertions.assertThat(userRepository.findByUsername("younes"))
                .isEqualToIgnoringGivenFields(userService.findUserByLogin(user.getUsername()));
    }
}