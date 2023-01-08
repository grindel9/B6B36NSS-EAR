package cz.cvut.ear.sem.service;

import cz.cvut.ear.sem.dao.UserDao;
import cz.cvut.ear.sem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));
    }

    @Transactional
    public User login(String username, String password) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        Optional<User> user = userDao.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Username does not exist");
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())){
            throw new UsernameNotFoundException("Password and encoded password does not match");
        }
        return user.get();
    }
}
