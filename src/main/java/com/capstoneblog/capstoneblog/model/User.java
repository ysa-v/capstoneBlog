package com.capstoneblog.capstoneblog.model;

import java.util.Objects;

public class User {

    private int userID;
    private String userName;
    private String userPassword;
    // 0 for employee, 1 for admin
    private int userRole;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserID() == user.getUserID() && getUserRole() == user.getUserRole() && getUserName().equals(user.getUserName()) && getUserPassword().equals(user.getUserPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getUserName(), getUserPassword(), getUserRole());
    }
}
