package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.AccessException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if(!meal.getUser().getId().equals(userId)){
            throw new AccessException("Current user doesn't have a right for this meal");
        }
        if (meal.isNew()) {
            em.persist(meal);
        } else {
            em.merge(meal);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = em.find(Meal.class,id);
        if(meal == null) return false;
        if(!meal.getUser().getId().equals(userId)){
            throw new AccessException("Current user doesn't have a right for this meal");
        }
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
      Meal meal = em.find(Meal.class,id);
      if(meal == null) return null;
      if(!meal.getUser().getId().equals(userId)){
          throw new AccessException("Current user doesn't have a right for this meal");
      }
      else return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED,Meal.class).setParameter("userId",userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {

        return em.createNamedQuery(Meal.BETWEEN_RANGE,Meal.class)
                .setParameter("userId",userId)
                .setParameter("startDateTime",startDateTime)
                .setParameter("endDateTime",endDateTime)
                .getResultList();
    }
}