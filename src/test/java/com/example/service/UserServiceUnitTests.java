package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;


    @Test
    void User_Password_Should_Match() {
        User user = userSetup1();
        userRepository.save(user);

        User userInput = new User();
        userInput.setId(2L);
        userInput.setPassword("pass123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean passwordMatches = userServiceImpl.userPasswordMatches(user.getId(), userInput);
        assertThat(passwordMatches).isTrue();
    }

    @Test
    void Should_Get_All_Users() {
        User Aljaz = userSetup1();
        User Nejc = userSetup2();
        List<User> expectedUserList = List.of(Aljaz, Nejc);
        userRepository.saveAll(expectedUserList);

        when(userRepository.findAll()).thenReturn(Optional.of(expectedUserList).get());

        List<User> actualUserList = userRepository.findAll();
        assertThat(expectedUserList.size()).isEqualTo(actualUserList.size());
    }

    @Test
    void Should_Delete_User() {
        User Aljaz = userSetup1();
        userServiceImpl.registerUser(Aljaz);
        userRepository.deleteById(Aljaz.getId());
        assertThat(userRepository.findById(Aljaz.getId()).isEmpty());
    }

    @Test
    void Should_Update_User() {
        User existingUser = userSetup1();
        userServiceImpl.registerUser(existingUser);
        String usernameBeforeChange = existingUser.getUsername();


        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        User newUser = new User();
        newUser.setUsername("aljaz1111");
        newUser.setEmail("aljaz.poglavc23@gmail.com");
        newUser.setId(5L);
        newUser.setPassword("pass123");

        userServiceImpl.updateUser(newUser, existingUser.getId());

        assertThat(usernameBeforeChange).isNotEqualTo(newUser.getUsername());

    }

    User userSetup1() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(1L);
        user.setEmail("aljaz.poglavc1@gmail.com");
        user.setUsername("aljaz23");
        user.setPassword(encoder.encode("pass123"));
        return user;
    }

    User userSetup2() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(2L);
        user.setEmail("nejc.poglavc1@gmail.com");
        user.setUsername("nejc23");
        user.setPassword(encoder.encode("nejc252"));
        return user;
    }


}
