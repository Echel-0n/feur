package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.Subscription;
import projet.jee.repository.ActivityRepository;
import projet.jee.repository.SubscriptionRepository;
import projet.jee.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    public SubscriptionRepository subscriptionRepository;
    @Autowired
    public ActivityRepository activityRepository;
    @Autowired
    public UserRepository userRepository;
    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    @Override
    public List<Subscription> findAll(){
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> findByUserId(Long id) {
        return subscriptionRepository.findBySubscriptionID_User_UserId(id);
    }

    @Override
    public List<Subscription> findByActivityId(Long id) {
        return subscriptionRepository.findBySubscriptionID_Activity_ActivityId(id);
    }

    @Override
    public Subscription findByUserAndActivity(Long userId, Long activityId) {
        return subscriptionRepository.findBySubscriptionID_User_UserIdAndSubscriptionID_Activity_ActivityId(userId, activityId);
    }
}