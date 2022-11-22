package com.example.service;

import com.example.model.User;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User registerUser(User user);

    List<User> getAllUsers();

    void deleteUser(long id);

    User updateUser(User user, long id) throws IOException, ParserConfigurationException;

    boolean userPasswordMatches(long id, User user);

}
