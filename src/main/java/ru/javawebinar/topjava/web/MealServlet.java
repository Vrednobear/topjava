package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private Storage storage;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealMapStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            log.info("getAll");
            req.setAttribute("mealsTo", MealsUtil.getMealsWithExcessParameter(storage.getAllSorted(), MealsUtil.CALORIES_PER_DAY));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }


        Meal m = null;
        switch (action) {
            case "add":
                m = new Meal(null, null, 0);
                break;
            case "delete":
                Integer id = getId(req);
                log.info("Delete {}" ,id);
                storage.delete(id);
                resp.sendRedirect("meals");
                return;
            case "update":
                m = storage.get(getId(req));
        }
        req.setAttribute("meal", m);
        req.setAttribute("storage", storage);
        req.setAttribute("action", action);
        req.getRequestDispatcher("/update.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);
        String date = req.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date, TimeUtil.getFormatter());
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id,dateTime,description,calories);

        log.info(meal.getId()==null? "Create {}":"Update {}",meal);
        storage.save(meal);
        resp.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request){
        String id = Objects.requireNonNull(request.getParameter("id"));
        return  Integer.parseInt(id);

    }
}
