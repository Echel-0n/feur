package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.jee.entity.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Subscription findByUser_UserIdAndActivity_ActivityId(Long userId, Long activityId);
    List<Subscription> findByUser_UserId(Long userId);
    List<Subscription> findByActivity_ActivityId(Long activityId);
}
