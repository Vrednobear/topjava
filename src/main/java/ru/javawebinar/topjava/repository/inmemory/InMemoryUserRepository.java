package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);
    @Override
    public User save(User user) {
        logger.info("save {}",user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        logger.info("delete {}",id);
        return true;
    }

    @Override
    public User get(int id) {
        logger.info("get {}",id);
        return null;
    }

    @Override
    public User getByEmail(String email) {
        logger.info("getByEmail {}",email);
        return null;
    }

    @Override
    public List<User> getAll() {
        logger.info("Get all");
        return Collections.EMPTY_LIST;
    }
}
