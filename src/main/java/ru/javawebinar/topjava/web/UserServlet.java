package ru.javawebinar.topjava.web;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
       request.getRequestDispatcher("/users.jsp").forward(request,response);
     //   response.sendRedirect("/topjava/users.jsp");
        response.sendRedirect("users.jsp");
}
}
