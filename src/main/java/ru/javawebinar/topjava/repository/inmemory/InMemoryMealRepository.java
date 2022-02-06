package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.security.Security;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal,meal.getUserId()));
    }

    @Override
    public List<Meal> getAll() {
        List <Meal> meals = new ArrayList<>(repository.values());
        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return meals;
    }

    @Override
    public List<MealTo> getFiltered(int userId,int userCalories, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredByDateTimeForUser(getAll(),userCalories,userId ,startDate,startTime,endDate,endTime);
    }
    @Override
    public Meal get(int id,int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId() == userId) return meal;
        return null;
    }

    //TODO: 2 calls to repo to one

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal == null || meal.getUserId() != userId) return false;
        else return repository.remove(id) != null;
    }

    @Override
    public Meal save(Meal meal,int userId) {
        if(meal.getUserId() != userId) return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

}

