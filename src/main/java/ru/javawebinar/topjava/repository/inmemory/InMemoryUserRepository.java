package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> usersMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        logger.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            usersMap.put(user.getId(), user);
            return user;
        }
        return usersMap.computeIfPresent(user.getId(), (integer, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        logger.info("delete {}", id);
        return usersMap.remove(id) != null;
    }

    @Override
    public User get(int id) {
        logger.info("get {}", id);
        return usersMap.get(id);
    }

    @Override
    public User getByEmail(String email) {
        logger.info("getByEmail {}", email);
        return (usersMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<User> getAll() {
        logger.info("Get all");
        return usersMap.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

}
