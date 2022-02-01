package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final Map<String, User> repositoryByEmail = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        logger.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (integer, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        logger.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        logger.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        logger.info("getByEmail {}", email);
      return (repository.values().stream()
              .filter(user -> user.getEmail().equals(email))
              .collect(Collectors.toList())).get(1);
    }

    @Override
    public List<User> getAll() {
        logger.info("Get all");
        List<User> users = new ArrayList<>(repository.values());
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                int result = u1.getName().compareTo(u2.getName());
                if(result == 0){result = u1.getEmail().compareTo(u2.getEmail());}
                return result;
            }
        });
       return users;
    }
}
