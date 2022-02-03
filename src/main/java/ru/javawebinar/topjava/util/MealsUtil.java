package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000,SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500,SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000,SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500,SecurityUtil.authUserId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410,SecurityUtil.authUserId())
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

//    public static MealTo getTo(Meal meal, int caloriesPerDay) {
//        List<Meal> listForMeal = new ArrayList<>();
//        listForMeal.add(meal);
//        return filterByPredicate(listForMeal, caloriesPerDay, meal -> true);
//    }


    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay,
                                              LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(
                meal.getTime(), meal.getDate(),startDate,startTime,endDate,endTime));
    }

    public static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }



    public static List<MealTo> getTosForUser(Collection<Meal> meals, int caloriesPerDay, int userId) {
        return filterByPredicateForUser(meals, caloriesPerDay,userId, meal -> true);
    }

    public static List<MealTo> filterByPredicateForUser(Collection<Meal> meals, int caloriesPerDay, int userId, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        if(caloriesSumByDate.isEmpty()){
            return Collections.emptyList();
        }

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }



    public static List<MealTo> getFilteredByDateTimeForUser(Collection<Meal> meals, int caloriesPerDay, int userId,
                                                     LocalDate startDate, LocalTime startTime,
                                                     LocalDate endDate, LocalTime endTime) {
        return filterByPredicateForUser(meals, caloriesPerDay, userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(),meal.getDate(),
                startDate, startTime, endDate, endTime));
    }



    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess,meal.getUserId());
    }

    public static MealTo getTo(Collection<Meal> meals, Meal initialMeal,int caloriesPerDay, int userId){
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> meal.getId() == initialMeal.getId())
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList()).get(0);
    }
}
