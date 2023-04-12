package projet.jee.service;

import projet.jee.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    public User save(User user);
    public List<User> save(Iterable<User> users);

    public List<User> findAll();
    public Optional<User> findById(Long id);
    public User findByUsername(String username);
}
