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
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> fetchUserList(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> fetchUserById(Long id) {
        return userRepository.findById(id);
    }
}
