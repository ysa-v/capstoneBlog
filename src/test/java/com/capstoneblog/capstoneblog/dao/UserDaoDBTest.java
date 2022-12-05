package com.capstoneblog.capstoneblog.dao;


import com.capstoneblog.capstoneblog.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class UserDaoDBTest {

    @Autowired
    UserDao userDao;

    @BeforeEach
    void setUp() {
        List<User> users = userDao.getAllUsers();
        for(User user: users){
            userDao.deleteUserById(user.getUserID());
        }
    }

    @Test
    void TestAddAndGetUserByID() {
        User user = new User();
        user.setUserName("Test User");
        user.setUserPassword("Test Password");
        user.setUserRole(1);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserByID(user.getUserID());

        assertEquals(user, fromDao);

    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setUserName("Test User");
        user.setUserPassword("Test Password");
        user.setUserRole(1);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUserName("Test User");
        user2.setUserPassword("Test Password");
        user2.setUserRole(1);
        user2 = userDao.addUser(user2);

        List<User> users = userDao.getAllUsers();

        assertEquals(2,users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(user2));

    }

    @Test
    void deleteUserById() {

        User user = new User();
        user.setUserName("Test User");
        user.setUserPassword("Test Password");
        user.setUserRole(1);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserByID(user.getUserID());
        assertEquals(user, fromDao);

        userDao.deleteUserById(user.getUserID());

        fromDao = userDao.getUserByID(user.getUserID());
        assertNull(fromDao);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setUserName("Test User");
        user.setUserPassword("Test Password");
        user.setUserRole(1);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserByID(user.getUserID());
        assertEquals(user, fromDao);

        user.setUserPassword("Test Password2");
        userDao.updateUser(user);

        assertNotEquals(user, fromDao);

        fromDao = userDao.getUserByID(user.getUserID());
        assertEquals(user, fromDao);
    }
}