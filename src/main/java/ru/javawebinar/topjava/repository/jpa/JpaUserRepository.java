package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
         if(user.isNew()){
             em.persist(user);
         }else{
             em.merge(user);
         }
         return user;
    }

    @Override
    public boolean delete(int id) {
      return em.createNamedQuery(User.DELETE)
               .setParameter("id",id)
               .executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
      return  em.find(User.class,id);
    }

    @Override
    public User getByEmail(String email) {
        return em.createNamedQuery(User.BY_EMAIL,User.class)
                .setParameter(1,email)
                .getSingleResult();
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED,User.class).getResultList();
    }
}
