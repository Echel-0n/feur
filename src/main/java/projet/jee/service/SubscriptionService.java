package projet.jee.service;

import projet.jee.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    public Subscription save(Subscription subscription);
    public List<Subscription> findAll();
    public Optional<Subscription> findById(Long id);
    public List<Subscription> findByUserId(Long id);
    public List<Subscription> findByActivityId(Long id);
    public Subscription findByUserAndActivity(Long userId, Long activityId);
}