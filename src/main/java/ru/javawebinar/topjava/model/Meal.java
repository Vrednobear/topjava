package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(name = Meal.DELETE,query = "DELETE FROM Meal m WHERE m.id=:id"),
        @NamedQuery(name = Meal.BY_USER_ID,query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user  " +
                "WHERE m.user.id=:userId"),
        @NamedQuery(name = Meal.ALL_SORTED,query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user " +
                "WHERE m.user.id=:userId  ORDER BY m.dateTime desc"),
        @NamedQuery(name = Meal.BETWEEN_RANGE,query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user " +
                "WHERE m.user.id=:userId AND m.dateTime >=:startDateTime AND m.dateTime <:endDateTime "),
})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String BY_USER_ID = "Meal.getByUserId";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BETWEEN_RANGE = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time",nullable = false,unique = true,columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description",nullable = false)
    @NotNull
    @Length(min = 2)
    private String description;

    @Column(name = "calories",nullable = false)
    @Range(min=0, max = 5000)
    private int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories &&
                dateTime.equals(meal.dateTime) &&
                description.equals(meal.description) &&
                user.equals(meal.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTime, description, calories, user);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
