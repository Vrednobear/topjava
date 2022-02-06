package ru.javawebinar.topjava;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
            adminRestController.create(new User(null,"userName","email","pass", Role.ADMIN));

            System.out.println(DateTimeUtil.isBetweenHalfOpen(LocalTime.of(15,0),LocalDate.of(2020,Month.JANUARY,30),
                                            LocalDate.of(2020,Month.JANUARY,30),LocalTime.of(10,0),
                                            LocalDate.of(2020,Month.JANUARY,30),LocalTime.of(20,0)));


            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll();
            System.out.println( mealRestController.getAll());
            System.out.println(mealRestController.getFiltered(LocalDate.of(2020, Month.JANUARY,30), LocalTime.of(0,0),
                                            LocalDate.of(2020,Month.JANUARY,31), LocalTime.of(21,0)));
        }

    }
}
