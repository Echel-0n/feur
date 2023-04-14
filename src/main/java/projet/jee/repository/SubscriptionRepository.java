package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projet.jee.entity.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    @Transactional
    @Modifying
    void deleteBySubscriptionID_User_UserIdAndSubscriptionID_Activity_ActivityId(Long userId, Long activityId);
    @Transactional
    @Modifying
    void deleteBySubscriptionID_User_UserId(Long userId);
    @Transactional
    @Modifying
    void deleteBySubscriptionID_Activity_ActivityId(Long activityId);

    List<Subscription> findBySubscriptionID_User_UserId(Long userId);

    List<Subscription> findBySubscriptionID_Activity_ActivityId(Long activityId);

    Subscription findBySubscriptionID_User_UserIdAndSubscriptionID_Activity_ActivityId(Long userId, Long activityId);
}
