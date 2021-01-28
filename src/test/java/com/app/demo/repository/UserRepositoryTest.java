package com.app.demo.repository;

import com.app.demo.entity.Role;
import com.app.demo.entity.User;
import com.app.demo.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
 public class UserRepositoryTest {
 @Rule
 public ExpectedException exceptionRule = ExpectedException.none();
 @Autowired
 private UserService userService ;
 @MockBean
 private UserRepository userRepository ;
 @Test
 public void FindUserTest() {
  List<Role> roles = null ;
  User user = new User();
  user.setId(3L);
  user.setUsername("younes");
  user.setPassword("younes123");
  user.setRoles(roles);

  Optional<User> optional = Optional.of(user);
  given(userRepository.findByUsername("younes")).willReturn(user);
  assertEquals(userService.findUserByLogin(user.getUsername()), userRepository.findById(3L).get().getUsername());
  Assertions.assertThat(userRepository.findById(3L).get())
          .isEqualToIgnoringGivenFields(userService.findUserByLogin(user.getUsername()));
 }

}