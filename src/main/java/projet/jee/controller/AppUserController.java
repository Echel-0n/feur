package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.Abonnement;
import projet.jee.entity.Activity;
import projet.jee.entity.AppUser;
import projet.jee.service.AbonnementService;
import projet.jee.service.ActivityService;
import projet.jee.service.AppUserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppUserController {
    @Autowired
    private AppUserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AbonnementService abonneService;


    @PostMapping("/users/users")
    public AppUser saveUser(@RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users/users")
    public List<AppUser> fetchUserList(){
        return userService.fetchUserList();
    }

    @GetMapping("/users/users/{id}")
    public Optional<AppUser> fetchUserById(@PathVariable("id") Long id){
        return userService.fetchUserById(id);
    }

    @PostMapping("/activities")
    public Activity saveActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @GetMapping("/activities")
    public List<Activity> fetchActivityList(){
        return activityService.fetchActivityList();
    }

    @GetMapping("/activities/{id}")
    public Optional<Activity> fetchActivityById(@PathVariable("id") Long id){
        return activityService.fetchActivityById(id);
    }
    @PostMapping("/abonnes")
    public Abonnement saveAbonne(@RequestBody Abonnement abonne) {
        return abonneService.saveAbonne(abonne);
    }

    @GetMapping("/abonnes")
    public List<Abonnement> fetchAbonneList(){
        return abonneService.fetchAbonneList();
    }

}
