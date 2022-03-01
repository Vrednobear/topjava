package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService mealService;

    @Test
    public void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Integer id = created.getId();
        Meal meal = getNew();
        meal.setId(id);
        assertMatch(mealService.get(id, USER_ID), meal);

    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> mealService.create(new Meal(null, MEAL1.getDateTime(), "desc", 1000), USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);

    }

    @Test
    public void getStrangeFood() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteStrangeFood() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = mealService.getBetweenInclusive(LocalDate.of(2020, 1, 30),
                LocalDate.of(2020, 1, 30), USER_ID);
        List<Meal> expected = List.of(MEAL3, MEAL2, MEAL1);
        assertMatch(meals, expected);
    }

    @Test
    public void getBetweenWithNullDates() {
        assertMatch(mealService.getBetweenInclusive(null, null, USER_ID), EXPECTED_LIST);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, EXPECTED_LIST);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void updateStrangeFood() {
        assertThrows(NotFoundException.class, () -> mealService.update(getUpdated(), ADMIN_ID));
    }


}