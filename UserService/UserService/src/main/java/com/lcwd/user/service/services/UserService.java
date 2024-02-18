package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    //save user
    User saveUser(User user);

    //get a single user
    User getUser(String userId);

    //get all users
    List<User> getAllUsers();
}
