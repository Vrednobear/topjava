package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 7;
    public static final int NOT_FOUND = 10;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, 1,30,10,0),"Breakfast",500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, LocalDateTime.of(2020, 1,30,13,0),"Lunch",1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, LocalDateTime.of(2020, 1,30,20,0),"Dinner",500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, LocalDateTime.of(2020, 1 , 31, 0, 0), "Border food", 100);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, LocalDateTime.of(2020, 1,31,10,0),"Breakfast",500);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, LocalDateTime.of(2020, 1,31,13,0),"Lunch",1000);
    public static final Meal MEAL7 = new Meal(MEAL1_ID + 6, LocalDateTime.of(2020, 1,31,20,0),"Dinner",510);

    public static final Meal MEAL8 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, 1,31,14,0),"Admin Lunch",510);
    public static final Meal MEAL9 = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, 1,31,21,0),"Admin dinner",1500);

    public static final List<Meal> EXPECTED_LIST = List.of(MEAL7,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);




    public static Meal getNew() {
        return new Meal(null,LocalDateTime.of(2022, 2,28,10,0),"New", 228);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID,LocalDateTime.of(2022, 2,28,10,0),"Updated",669);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
