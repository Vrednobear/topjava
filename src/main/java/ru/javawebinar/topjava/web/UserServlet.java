package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserServlet extends javax.servlet.http.HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setId(userId);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        log.debug("redirect to userList");
        response.sendRedirect("users.jsp");
    }
}
