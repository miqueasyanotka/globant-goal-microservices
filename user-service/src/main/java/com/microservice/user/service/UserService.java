package com.microservice.user.service;

import com.microservice.user.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User getUser(String userId);
}
