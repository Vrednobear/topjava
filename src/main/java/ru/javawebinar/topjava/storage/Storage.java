package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface Storage {

    void save(Meal meal);

    void delete(Integer id);

    int size();

    void clear();

    Meal get(Integer id);

    //TODO:Collection
    List<Meal> getAllSorted();

//    Meal get();
//
//    List<Meal> getAllSorted();


}
