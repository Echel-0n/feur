package projet.jee.service;

import projet.jee.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription save(Subscription subscription);
    List<Subscription> findAll();
    List<Subscription> findByUserId(Long id);
    List<Subscription> findByActivityId(Long id);
    Subscription findByUserAndActivity(Long userId, Long activityId);
    void deleteByActivity(Long activityId);void deleteByUser(Long userId);
    void unsubscribeByUserAndActivity(Long userId, Long activityId);
}