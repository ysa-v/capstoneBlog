package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.User;

import java.util.List;

public interface UserDao {

    User getUserByID(int id);

    List<User> getAllUsers();

    void deleteUserById (int id);

    void updateUser(User user);

    User addUser(User user);




}
