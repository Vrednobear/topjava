package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static Meal meal1;
    public static Meal meal2;
    public static Meal meal3;
    public static Meal meal4;
    public static Meal meal5;
    public static Meal meal6;
    public static Meal meal7;
    public static final Meal adminMeal1;
    public static final Meal adminMeal2;


    static {
        meal1 = new Meal(MEAL1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 500);
        meal2 = new Meal(MEAL1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Lunch", 1000);
        meal3 = new Meal(MEAL1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Dinner", 500);
        meal4 = new Meal(MEAL1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Border", 100);
        meal5 = new Meal(MEAL1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Breakfast", 500);
        meal6 = new Meal(MEAL1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Lunch", 1000);
        meal7 = new Meal(MEAL1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Dinner", 510);
        adminMeal1 = new Meal(ADMIN_MEAL_ID, of(2020, Month.JANUARY, 31, 14, 0), "Admin lunch", 510);
        adminMeal2 = new Meal(ADMIN_MEAL_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Admin dinner", 1500);

        meal1.setUser(USER);
        meal2.setUser(USER);
        meal3.setUser(USER);
        meal4.setUser(USER);
        meal5.setUser(USER);
        meal6.setUser(USER);
        meal7.setUser(USER);
        adminMeal1.setUser(ADMIN);
        adminMeal2.setUser(ADMIN);
    }

    public static final List<Meal> userMeals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    public static final List<Meal> userMealsAcs = Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
    public static final List<Meal> adminMeals = Arrays.asList(adminMeal2,adminMeal1);

    public static Meal getNew() {
        Meal meal = new Meal(of(2020, Month.FEBRUARY, 1, 18, 0), "Созданный ужин", 300);
        meal.setUser(USER);
        return meal;
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(MEAL1_ID, meal1.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак", 200);
        meal.setUser(USER);
        return meal;
    }

    public static void  assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);

    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
//        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
