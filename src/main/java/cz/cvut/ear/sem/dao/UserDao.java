package cz.cvut.ear.sem.dao;

import cz.cvut.ear.sem.exception.PersistenceException;
import cz.cvut.ear.sem.model.User;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDao extends BaseDao<User> {
    protected UserDao() {
        super(User.class);
    }

    public Optional<User> findByUsername(String username) {
        Objects.requireNonNull(username);
        return Optional.ofNullable(em.createNamedQuery("User.findByUsername",
                        User.class)
                .setParameter("username", username)
                .getSingleResult());
    }
}
