package projet.jee.service;

import projet.jee.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService{
    public AppUser saveUser(AppUser user);

    public List<AppUser> fetchUserList();
    public Optional<AppUser> fetchUserById(Long id);
}
