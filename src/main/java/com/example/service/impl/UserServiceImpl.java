package com.example.service.impl;

import com.example.exception.ResourceException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user, long id) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User existingUser = userRepository.findById(id).get();

        if (userPasswordMatches(id, user)) {
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(encoder.encode(user.getPassword()));
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Invalid password");
        }

        return userRepository.save(existingUser);
    }


    //WHEN UPDATING USER MUST INSERT CURRENT PASSWORD WHICH MUST MATCH
    @Override
    public boolean userPasswordMatches(long id, User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User existingUser = userRepository.findById(id).get();

        boolean isPasswordMatch = encoder.matches(user.getPassword(), existingUser.getPassword());

        return isPasswordMatch;
    }
}
