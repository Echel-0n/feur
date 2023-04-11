package projet.jee.service;

import projet.jee.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    public Subscription saveSubscription(Subscription subscription);

    public List<Subscription> fetchSubscriptionList();
    public Optional<Subscription> fetchSubscriptionById(Long id);
}
