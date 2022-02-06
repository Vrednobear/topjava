package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    //  private MealRepository repository;
    private MealRestController mealRestController;

    @Override
    public void init() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
//        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String formType = request.getParameter("form");
        if (formType.equals("add")) {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")), SecurityUtil.authUserId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) {
                mealRestController.create(meal);
            } else {
                mealRestController.update(meal, meal.getId());
            }
            response.sendRedirect("meals");
        } else {
            String sd = request.getParameter("startDate");
            String st = request.getParameter("startTime");
            String ed = request.getParameter("endDate");
            String et = request.getParameter("endTime");

            if (sd.equals("")) sd = LocalDate.MIN.toString();
            if (ed.equals("")) ed = LocalDate.MAX.toString();
            if (st.equals("")) st = LocalTime.MIN.toString();
            if (et.equals("")) et = LocalTime.MAX.toString();

            LocalDate startDate = LocalDate.parse(sd);
            LocalDate endDate = LocalDate.parse(ed);
            LocalTime startTime = LocalTime.parse(st);
            LocalTime endTime = LocalTime.parse(et);

            List<MealTo> mealsFiltered = mealRestController.getFiltered(startDate, startTime, endDate, endTime);
            request.setAttribute("meals", mealsFiltered);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "update":
                //TODO CHECK TYPE MEAL OR
                MealTo mealTo = mealRestController.get(getId(request));
                Integer a = mealTo.getId();
                Meal mealFromTo = new Meal(a, mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), mealTo.getUserId());
                request.setAttribute("meal", mealFromTo);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
