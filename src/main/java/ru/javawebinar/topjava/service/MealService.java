package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll(int userId, int userCalories) {
        List<MealTo> list = MealsUtil.getTosForUser(repository.getAll(userId), userCalories, userId);
        if (list.isEmpty()) return Collections.emptyList();
        else return list;
    }

    public List<MealTo> getFiltered(int userId, int userCalories, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealTo> list = repository.getFiltered(userId, userCalories, startDate, startTime, endDate, endTime);
        if (list.isEmpty()) return Collections.emptyList();
        else return list;
    }

    public MealTo get(int id, int userId, int userCalories) {
        List<MealTo> list = MealsUtil.getTosForUser(repository.getAll(userId), userCalories, userId);
        
        if (list.isEmpty()) {
            throw new NotFoundException("The meal with id = " + id + "not found for user " + userId);
        }
        MealTo gottenMealTo = list.stream()
                .filter(mealTo -> mealTo.getId() == id)
                .findFirst().get();
        ValidationUtil.checkNotFoundWithId(gottenMealTo, id);
        return gottenMealTo;
    }

    public void delete(int id, int userId) {
        boolean result = repository.delete(id, userId);
        ValidationUtil.checkNotFoundWithId(result, id);
    }

    public MealTo create(Meal meal, int userId, int userCaloriesPerDay) {
        Meal savedMeal = repository.save(meal, userId);
        ValidationUtil.checkNotFoundWithId(savedMeal, meal.getId());
        return MealsUtil.getTo(repository.getAll(userId), meal, userCaloriesPerDay, userId);
    }

    public void update(Meal meal, int userId) {
        Meal savedMeal = repository.save(meal, userId);
        ValidationUtil.checkNotFoundWithId(savedMeal, meal.getId());
    }
}
