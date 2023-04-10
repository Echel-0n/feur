package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.AppUser;
import projet.jee.service.AppUserService;

import java.util.List;
import java.util.Optional;

@RestController
public class AppUserController {
    @Autowired
    private AppUserService userService;

    @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users")
    public List<AppUser> fetchUserList(){
        return userService.fetchUserList();
    }
    @GetMapping("/users/{id}")
    public Optional<AppUser> fetchUserById(@PathVariable("id") Long id){
        return userService.fetchUserById(id);
    }
}
