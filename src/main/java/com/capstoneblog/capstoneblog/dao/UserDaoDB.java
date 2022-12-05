package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoDB implements UserDao{

    @Autowired
    JdbcTemplate jdbc;

    public static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException{
            User user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUserName(rs.getString("userName"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setUserRole(rs.getInt("userRole"));
            return user;
        }
    }


    @Override
    public User getUserByID(int id) {
        try {
            final String GET_USER_BY_ID = "SELECT * FROM user WHERE userID = ?";
            return jdbc.queryForObject(GET_USER_BY_ID, new UserMapper(), id);
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try{
            final String GET_ALL_USERS = "SELECT * FROM user";
            return jdbc.query(GET_ALL_USERS, new UserMapper());
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public void deleteUserById(int id) {
        try{
            final String DELETE_USER_BY_ID = "DELETE * FROM user WHERE userID = ?";
        } catch (DataAccessException ignored){

        }
    }

    @Override
    public void updateUser(User user) {
        try{
            final String UPDATE_USER = "UPDATE user SET userName =? userPassword = ?, userRole = ? " +
                    "WHERE userID =?";
            jdbc.update(UPDATE_USER,
                    user.getUserName(),
                    user.getUserPassword(),
                    user.getUserRole(),
                    user.getUserID());
        } catch (DataAccessException ignored){
        }

    }

    @Override
    public User addUser(User user) {
        try {
            final String ADD_USER = "INSERT INTO user(userName, userPassword, userRole) VALUES (?,?,?)";
            jdbc.update(ADD_USER,
                user.getUserName(),
                user.getUserPassword(),
                user.getUserRole());

            int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            user.setUserID(newID);
            return user;
        } catch (DataAccessException ex){
            return null;
        }
    }
}
