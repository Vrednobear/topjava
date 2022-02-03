package ru.javawebinar.topjava.service;

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

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    //TODO: REFACTOR getAll and getFiltered

    //USER ID????
    public List<MealTo> getAll(int userId, int userCalories) {
        List<MealTo> list = MealsUtil.getTosForUser(repository.getAll(), userCalories, userId);
        if(list.isEmpty()) return Collections.emptyList();
        else return list;
    }

    //USER ID???
    public List<MealTo> getFiltered(int userId, int userCalories, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealTo> list = repository.getFiltered(userId, userCalories, startDate, startTime, endDate, endTime);
        if(list.isEmpty()) return Collections.emptyList();
        else return list;
    }

    public MealTo get(int id, int userId, int userCalories) {
        List<MealTo> list = MealsUtil.getTosForUser(repository.getAll(), userCalories, userId);
        if (list.isEmpty()) {
            throw new NotFoundException("The meal with id = " + id + "not found for user " + userId);
        }

        MealTo gottenMealTo = list.stream()
                .filter(mealTo -> mealTo.getId() == id)
                .collect(Collectors.toList())
                .get(0);
        if(gottenMealTo == null){
            throw new NotFoundException("The meal with id = " + id + "not found for user " + userId);
        }

        return gottenMealTo;
    }

    public void delete(int id, int userId) {
      boolean result = repository.delete(id, userId);
      if(result) throw new NotFoundException("The meal with id = " + id + "for user " + userId + "is not found");
    }

    public MealTo create(Meal meal, int userId, int userCaloriesPerDay) {
      Meal savedMeal = repository.save(meal,userId);
        if(savedMeal == null){
            throw new NotFoundException("The meal " + meal + " is not for user " + meal.getUserId());
        }
     return MealsUtil.getTo(repository.getAll(),meal,userCaloriesPerDay,userId);
    }

    public void update(Meal meal, int userId) {
       Meal savedMeal =  repository.save(meal,userId);
       if(savedMeal == null){
           throw new NotFoundException("The meal " + meal + " is not for user " + meal.getUserId());
       }
    }
}
