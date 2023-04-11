package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.jee.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
}
