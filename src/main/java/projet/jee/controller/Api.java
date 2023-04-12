package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.Activity;
import projet.jee.entity.Subscription;
import projet.jee.entity.SubscriptionShadow;
import projet.jee.entity.User;
import projet.jee.error.AlreadyExistException;
import projet.jee.error.ErrorMessage;
import projet.jee.error.NotFoundException;
import projet.jee.service.ActivityService;
import projet.jee.service.SubscriptionService;
import projet.jee.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Api {
    // Importation des services
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // API User

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) throws AlreadyExistException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistException("Username already taken");
        }
    }

    @PostMapping(value = "/users2")
    public List<User> saveUser(@RequestBody Iterable<User> users) throws AlreadyExistException {
        try {
            return userService.save(users);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistException("Username already taken");
        }
    }

    @GetMapping("/users")
    public List<User> fetchUserList(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User fetchUserById(@PathVariable("id") Long id) throws NotFoundException {
        Optional<User> ou =  userService.findById(id);
        if(ou.isEmpty()){
            throw new NotFoundException("There is no User with id "+id);
        }
        return ou.get();
    }

    @PostMapping("users/{userId}/subscribe/{activityId}")
    public Subscription subscribeUserToActivity(
            @PathVariable("userId") Long userId,
            @PathVariable("activityId") Long activityId
    ) throws NotFoundException {
        return subscribeUserToActivity(
                new SubscriptionShadow(userId, activityId)
        );
    }


    // API Activity

    @PostMapping("/activities")
    public Activity saveActivity(@RequestBody Activity activity) {
        return activityService.save(activity);
    }

    @GetMapping("/activities")
    public List<Activity> findActivityList(){
        return activityService.findAll();
    }

    @GetMapping("/activities/{id}")
    public Activity findActivityById(@PathVariable("id") Long id) throws NotFoundException {
        Optional<Activity> oa =  activityService.findById(id);
        if(oa.isEmpty()){
            throw new NotFoundException("There is no Activity with id "+id);
        }
        return oa.get();
    }


    // API Subscription

//    @GetMapping("/subscriptions")
//    public List<Subscription> findAllSubscription(){
//        return subscriptionService.findAll();
//    }

    @PostMapping("/subscriptions")
    public Subscription subscribeUserToActivity(
            @RequestBody SubscriptionShadow subscriptionShadow
    ) throws NotFoundException {
        Subscription s = new Subscription();

        Optional<Activity> oa = activityService.findById(subscriptionShadow.getActivity());
        Optional<User> ou = userService.findById(subscriptionShadow.getUser());

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
        s = subscriptionService.save(s);
        return s;
    }

    @GetMapping("/subscriptions/{id}")
    public Subscription findSubscriptionById(
            @PathVariable("id") Long id
    ) throws NotFoundException {
        Optional<Subscription> os =  subscriptionService.findById(id);
        if(os.isEmpty()){
            throw new NotFoundException("There is no Subscriber with id "+id);
        }
        return os.get();
    }

    @GetMapping("/subscriptions/user/{userId}")
    public List<Subscription> findSubscriptionByUser(
            @PathVariable("userId") Long userId
    ) throws NotFoundException {
        return subscriptionService.findByUserId(userId);
    }

    @GetMapping("/subscriptions/activity/{activityId}")
    public List<Subscription> findSubscriptionByActivity(
            @PathVariable("activityId") Long activityId
    ) throws NotFoundException {
        return subscriptionService.findByActivityId(activityId);
    }

    @GetMapping(value = "/subscriptions", params = {"user","activity"})
    public Subscription findSubscriptionByUserAndActivity(
            @RequestParam("user") Long userId,
            @RequestParam("activity") Long activityId
    ) {
        return subscriptionService.findByUserAndActivity(userId, activityId);
    }


    // Exception API TODO

    @RestController
    @RequestMapping("/api-key")
    public static class ApiNoKey {
        @GetMapping("/missing")
        public ErrorMessage apiKeyMissing(){
            return new ErrorMessage(HttpStatus.BAD_REQUEST, "API Key missing");
        }
        @GetMapping("/bad")
        public ErrorMessage apiKeyBad(){
            return new ErrorMessage(HttpStatus.BAD_REQUEST, "Bad API Key");
        }
    }
}
