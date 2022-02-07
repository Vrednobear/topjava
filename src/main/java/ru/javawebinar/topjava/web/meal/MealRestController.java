package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    Logger logger = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        logger.info("get all");
        return service.getAll(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        logger.info("get filtered between {} {} and {} {}", startDate, startTime, endDate, endTime);
        return service.getFiltered(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay(), startDate, startTime, endDate, endTime);
    }

    public MealTo get(int id) {
        logger.info("get with id = {}", id);
        return service.get(id, SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public void delete(int id) {
        logger.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public MealTo create(Meal meal) {
        ValidationUtil.checkNew(meal);
        logger.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        ValidationUtil.assureIdConsistent(meal, id);
        logger.info("update {} with id = {}", meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

}