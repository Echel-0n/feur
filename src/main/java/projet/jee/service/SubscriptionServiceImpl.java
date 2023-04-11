package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.Subscription;
import projet.jee.repository.SubscriptionRepository;
import projet.jee.repository.ActivityRepository;
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
    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    @Override
    public List<Subscription> fetchSubscriptionList(){
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> fetchSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }
}
