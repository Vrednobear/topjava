package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    Storage storage;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealMapStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("getAll");
        Integer id = null;
        String idStr = req.getParameter("id");
        if (idStr != null) {
            id = Integer.valueOf(idStr);
        }
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("mealsTo", MealsUtil.getMealsWithExcessParameter(storage.getAllSorted(), MealsUtil.CALORIES_PER_DAY));
            req.setAttribute("formatter", formatter);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }


        Meal m = null;
        switch (action) {
            case "add":
                m = new Meal(null, null, 0);
                break;
            case "delete":
                storage.delete(id);
                resp.sendRedirect("meals");
                return;
            case "update":
                m = storage.get(id);
        }
        req.setAttribute("meal", m);
        req.setAttribute("storage", storage);
        req.setAttribute("action", action);
        req.setAttribute("formatter", formatter);
        req.getRequestDispatcher("/update.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);
        String date = req.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id,dateTime,description,calories);

        log.info(meal.getId()==null? "Create {}":"Update {}",meal);
        storage.save(meal);
        resp.sendRedirect("meals");
    }
}
