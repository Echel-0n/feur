package projet.jee.service;

import projet.jee.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    public User saveUser(User user);

    public List<User> fetchUserList();
    public Optional<User> fetchUserById(Long id);
}
