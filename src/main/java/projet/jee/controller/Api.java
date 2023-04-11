package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.Subscription;
import projet.jee.entity.Activity;
import projet.jee.entity.SubscriptionShadow;
import projet.jee.entity.User;
import projet.jee.error.NotFoundException;
import projet.jee.service.SubscriptionService;
import projet.jee.service.ActivityService;
import projet.jee.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class Api {
    // Importation des services
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SubscriptionService subscriptionService;

    public Api() {

    }


    // API User

    @PostMapping("/api/users")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/api/users")
    public List<User> fetchUserList(){
        return userService.fetchUserList();
    }

    @GetMapping("/api/users/{id}")
    public User fetchUserById(@PathVariable("id") Long id) throws NotFoundException {
        Optional<User> ou =  userService.fetchUserById(id);
        if(ou.isEmpty()){
            throw new NotFoundException("There is no User with id "+id);
        }
        return ou.get();
    }

    @PostMapping("/api/users/{user_id}/subscribe/{activity_id}")
    public Subscription subscribeUserToActivity(
            @PathVariable("user_id") Long user_id,
            @PathVariable("activity_id") Long activity_id
    ) throws NotFoundException {
        return subscribeUserToActivity(
                new SubscriptionShadow(user_id, activity_id)
        );
    }


    // API Activity

    @PostMapping("/api/activities")
    public Activity saveActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @GetMapping("/api/activities")
    public List<Activity> fetchActivityList(){
        return activityService.fetchActivityList();
    }

    @GetMapping("/api/activities/{id}")
    public Activity fetchActivityById(@PathVariable("id") Long id) throws NotFoundException {
        Optional<Activity> oa =  activityService.fetchActivityById(id);
        if(oa.isEmpty()){
            throw new NotFoundException("There is no Activity with id "+id);
        }
        return oa.get();
    }


    // API Subscription

    @GetMapping("/api/subscriptions")
    public List<Subscription> fetchSubscriptionList(){
        return subscriptionService.fetchSubscriptionList();
    }

    @PostMapping("/api/subscriptions")
    public Subscription subscribeUserToActivity(
            @RequestBody SubscriptionShadow subscriptionShadow
    ) throws NotFoundException {
        Subscription s = new Subscription();

        Optional<Activity> oa = activityService.fetchActivityById(subscriptionShadow.getActivity());
        Optional<User> ou = userService.fetchUserById(subscriptionShadow.getUser());

        if(oa.isEmpty()){
            throw new NotFoundException("Unknown Activity");
        }
        if (ou.isEmpty()) {
            throw new NotFoundException("Unknown User");
        }
        Activity a = oa.get();
        User u = ou.get();
        s.setActivity(a);
        s.setUser(u);
        s = subscriptionService.saveSubscription(s);
        return s;
    }

}
