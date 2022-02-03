package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal,int userId);

    // false if meal do not belong to userId
    boolean delete(int id,int userId);

    // null if meal do not belong to userId
    Meal get(int id,int userId);

    // ORDERED dateTime desc
    List<Meal> getAll();

    List<MealTo> getFiltered(int userId,int userCalories, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
