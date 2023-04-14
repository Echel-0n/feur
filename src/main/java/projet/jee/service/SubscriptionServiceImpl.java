package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.Subscription;
import projet.jee.repository.SubscriptionRepository;

import java.util.List;
@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    public SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    @Override
    public List<Subscription> findAll(){
        return subscriptionRepository.findAll();
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

    @Override
    public void deleteByActivity(Long activityId) {
        subscriptionRepository.deleteBySubscriptionID_Activity_ActivityId(activityId);
    }

    @Override
    public void deleteByUser(Long userId) {
        subscriptionRepository.deleteBySubscriptionID_User_UserId(userId);
    }

    @Override
    public void unsubscribeByUserAndActivity(Long userId, Long activityId) {
        subscriptionRepository.deleteBySubscriptionID_User_UserIdAndSubscriptionID_Activity_ActivityId(userId, activityId);
    }
}