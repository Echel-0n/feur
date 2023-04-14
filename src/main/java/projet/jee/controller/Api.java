package projet.jee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projet.jee.entity.Activity;
import projet.jee.entity.Subscription;
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

    @PatchMapping("/users")
    public void updateUser(@RequestBody User user) throws AlreadyExistException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.update(user);
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
                new Subscription.Shadow(userId, activityId, null)
        );
    }

    @DeleteMapping("users/{userId}/unsubscribe/{activityId}")
    public void unsubscribeUserToActivity(
            @PathVariable("userId") Long userId,
            @PathVariable("activityId") Long activityId
    ) {
        subscriptionService.unsubscribeByUserAndActivity(userId, activityId);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(Long id) {
        subscriptionService.deleteByUser(id);
        userService.delete(id);
    }


    // API Activity

    @PostMapping("/activities")
    public List<Activity> saveActivity(@RequestBody List<Activity> activities) {
//        List<Activity> res = new ArrayList<Activity>();
        for(Activity activity : activities){
            activityService.save(activity);
        }
        return activities;
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

    @DeleteMapping("/activities/{id}")
    public void deleteActivityById(@PathVariable Long id){
        subscriptionService.deleteByActivity(id);
        activityService.delete(id);
    }




    // API Subscription

    @GetMapping("/subscriptions")
    public List<Subscription> findAllSubscription(){
        return subscriptionService.findAll();
    }

    @PostMapping("/subscriptions")
    public Subscription subscribeUserToActivity(
            @RequestBody Subscription.Shadow subscriptionShadow
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
        s.setSubscriptionID(new Subscription.ID(u, a));
        s.setNote(subscriptionShadow.getNote());
        return subscriptionService.save(s);
    }

    @GetMapping("/subscriptions/user/{userId}")
    public List<Subscription> findSubscriptionByUserInURL(
            @PathVariable("userId") Long userId
    ) {
        return findSubscriptionByUser(userId);
    }

    @GetMapping("/subscriptions/activity/{activityId}")
    public List<Subscription> findSubscriptionByActivityInURL(
            @PathVariable("activityId") Long activityId
    ) {
        return findSubscriptionByActivity(activityId);
    }

    @GetMapping(value = "/subscriptions", params = {"user"})
    public List<Subscription> findSubscriptionByUser(
            @RequestParam("user") Long userId
    ) {
        return subscriptionService.findByUserId(userId);
    }
    @GetMapping(value = "/subscriptions", params = {"activity"})
    public List<Subscription> findSubscriptionByActivity(
            @RequestParam("activity") Long activityId
    ) {
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
