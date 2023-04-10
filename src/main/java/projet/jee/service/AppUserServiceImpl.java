package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.AppUser;
import projet.jee.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    private AppUserRepository userRepository;
    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }
    @Override
    public List<AppUser> fetchUserList(){
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> fetchUserById(Long id) {
        return userRepository.findById(id);
    }
}
