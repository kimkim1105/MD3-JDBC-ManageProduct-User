package com.codegym.service.user;

import com.codegym.model.Customer;
import com.codegym.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    public void add(User user) throws SQLException;

    public User findByUsername(String username);

    public List<User> findAll();

    public boolean delete(int id) throws SQLException;

    public boolean update(User user) throws SQLException;

}
