package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.User;
import com.codegym.service.customer.CustomerDAO;
import com.codegym.service.customer.ICustomerDAO;
import com.codegym.service.user.IUserDAO;
import com.codegym.service.user.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    IUserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "signup":
                    addUserForm(request,response);
                break;
//            default:
//                showList(request, response);
//                break;
        }
    }

    private void addUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/register.jsp");
        dispatcher.forward(request,response);
    }
//
//    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
//        List<User> users = userDAO.findAll();
//        request.setAttribute("users", users);
//        dispatcher.forward(request, response);
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
//            case "create":
//                try {
//                    save(request, response);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                break;
            case "signup":
                try {
                    createUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "login":
                login(request, response);
                break;
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
//        if (userDAO.findAll()==null){
//            id = 1;
//        }else {
//            id = userDAO.findAll().get(userDAO.findAll().size()-1).getId()+1;
//        }
        RequestDispatcher dispatcher;
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (username==""||email==""||password==""){
            request.setAttribute("mess3","have '*' need to fill");
            dispatcher = request.getRequestDispatcher("user/register.jsp");
            dispatcher.forward(request,response);
            return;
        }
        if (!checkUsername(username)){
            request.setAttribute("mess1","username has existed");
            dispatcher = request.getRequestDispatcher("user/register.jsp");
            dispatcher.forward(request,response);
            return;
        }
        if (!checkEmail(email)){
            request.setAttribute("mess2","email has existed");
            dispatcher = request.getRequestDispatcher("user/register.jsp");
            dispatcher.forward(request,response);
            return;
        }

        userDAO.add(new User(username, firstname, lastname, email, password));
        response.sendRedirect("/products");
    }
    public boolean checkUsername(String username){
        List<User> users = userDAO.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
    public boolean checkEmail(String email){
        List<User> users = userDAO.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (checkLogin(username, password)) {
            response.sendRedirect("/products");
//            HttpSession session = request.getSession();
//            session.setAttribute("user", username);
        }
    }

    public boolean checkLogin(String username, String password) {
        List<User> users = userDAO.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                if (users.get(i).getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

}
