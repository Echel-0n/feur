package projet.jee.service;

import projet.jee.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    User save(User user);
    void update(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    User findByUsername(String username);

    void delete(Long id);
}
