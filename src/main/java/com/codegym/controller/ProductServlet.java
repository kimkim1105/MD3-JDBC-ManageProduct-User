package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Customer;
import com.codegym.model.Product;
import com.codegym.service.category.CategoryService;
import com.codegym.service.category.CategoryServiceImpl;
import com.codegym.service.product.ProductService;
import com.codegym.service.product.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    ProductService productService = new ProductServiceImpl();
    CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "create":
                showCreateForm(request,response);
                break;
            case "view":
                showViewForm(request,response);
                break;
            case "edit":
                showEditForm(request,response);
                break;
            case "delete":
                try {
                    deleteProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                showList(request,response);
                break;
        }

    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request,response);
        }else {
            productService.delete(id);
            response.sendRedirect("/products");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
        } else {
            request.setAttribute("product", product);
            dispatcher = request.getRequestDispatcher("product/edit.jsp");
        }
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        Category category = categoryService.findById(product.getCategoryId());
        RequestDispatcher dispatcher;
        if (product==null){
            dispatcher = request.getRequestDispatcher("error.jsp");
        }else {
            request.setAttribute("product",product);
            request.setAttribute("category",category);
            dispatcher = request.getRequestDispatcher("product/view.jsp");
        }
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request,response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        List<Product> products = productService.findAll();
        List<Category> categories = findAllCategory(products);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        System.out.println("category ======"+categories);
        dispatcher.forward(request, response);
    }

    List<Category> findAllCategory(List<Product> products) {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Category category = categoryService.findById(products.get(i).getCategoryId());
            list.add(category);
        }
        return list;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "create":
                try {
                    createProduct(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    editProduct(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryId = getCategoryId(request.getParameter("categoryName"));
        Product product = productService.findById(id);
        RequestDispatcher dispatcher = null;
        if (product == null) {
            dispatcher = request.getRequestDispatcher("error.jsp");
        } else {
            product.setName(name);
            product.setPrice(price);
            product.setCategoryId(categoryId);
            productService.update(product);
//            request.setAttribute("product", product);
//            dispatcher = request.getRequestDispatcher("index.jsp");
            response.sendRedirect("/products");
        }
//        try {
//            dispatcher.forward(request, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id;
        if (productService.findAll()==null){
            id = 1;
        }else {
            id = productService.findAll().get(productService.findAll().size()-1).getId()+1;
        }
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
//        int id = Integer.parseInt(request.getParameter("id"));
        int categoryId = getCategoryId(request.getParameter("categoryName"));
        productService.add(new Product(id,name,price,categoryId));
        response.sendRedirect("/products");
    }
    public int getCategoryId(String categoryName){
        int categoryId = 0;
        switch (categoryName){
            case "fruit":
                categoryId = 1;
                break;
            case "flower":
                categoryId = 2;
                break;
            case "animal":
                categoryId = 3;
                break;
        }
        return categoryId;
    }
}
