package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class MealMapStorage implements Storage {
   private final Map<Integer,Meal> mealsMap = new ConcurrentHashMap<>();
   private final AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(this::save);

    }

    @Override
    public void save(Meal meal) {
        if(meal.getId() == null){
            int id = counter.incrementAndGet();
            meal.setId(id);
            mealsMap.put(meal.getId(),meal);
        }
        mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(Integer id) {
        mealsMap.remove(id);
    }

    @Override
    public int size() {
        return mealsMap.size();
    }

    @Override
    public void clear() {
        mealsMap.clear();
    }

    @Override
    public Meal get(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public Collection<Meal> getAllSorted() {
        List<Meal> mealsList = new ArrayList<>(mealsMap.values());
        Collections.sort(mealsList, Comparator.comparing(Meal::getId));
        return mealsList;
    }

}
