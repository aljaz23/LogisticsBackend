package com.example.repository;

import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void Should_Save_User() {
        User user = userSetup();

        User savedUser = userRepository.save(user);
        User existingUser = testEntityManager.find(User.class, savedUser.getId());

        assertThat(existingUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void Should_Find_User_By_Username() {
        User user = userSetup();
        userRepository.save(user);
        User foundUser = userRepository.findByUsername(user.getUsername());
        assertThat(foundUser).isNotNull();
    }

    @Test
    void Should_Delete_user() {
        User user = userSetup();
        userRepository.save(user);
        userRepository.deleteById(user.getId());

        boolean userFound = userRepository.findById(user.getId()).isPresent();
        assertThat(userFound).isFalse();

    }

    User userSetup() {
        User user = new User();
        user.setEmail("aljaz.poglavc23@gmail.com");
        user.setUsername("funki23");
        user.setPassword("aljpogl23");

        return user;
    }

}
