package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.User;
import projet.jee.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public void update(User user) {
        userRepository.updateUsernameAndPasswordByUserId(user.getUsername(), user.getPassword(), user.getUserId());
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
