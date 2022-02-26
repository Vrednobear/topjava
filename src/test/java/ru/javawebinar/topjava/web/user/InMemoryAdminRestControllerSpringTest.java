package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Ignore
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp(){
        repository.init();
    }

    @Test
    public void delete(){
        controller.delete(UserTestData.USER_ID);
        Assert.assertNull(repository.get(UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFound(){
        Assert.assertThrows(NotFoundException.class, () -> controller.delete(UserTestData.NOT_FOUND));
    }
}
