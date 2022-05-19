package com.codegym.service.user;

import com.codegym.model.Customer;
import com.codegym.model.User;
import com.codegym.service.customer.ICustomerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;


public class UserDAO implements IUserDAO {

    public UserDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/checksql?useSSL=false", "root", "kimhue123@@@");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }


    @Override
    public void add(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into user (username, firstname, lastname, email, password) values (?,?,?,?,?)");) {
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getFirstname());
            preparedStatement.setString(3,user.getLastname());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.setString(5,user.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException e){}
    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        for (User u:
             findAll()) {
            if (u.getUsername().equals(username)){
                user = u;
            }
        }
        return user;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user ");) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                users.add(new User(id, username, firstname, lastname, email, password));
            }
        } catch (SQLException e) {
        }
        return users;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(User user) throws SQLException {
        return false;
    }

}
