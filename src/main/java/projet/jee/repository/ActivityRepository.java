package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projet.jee.entity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Transactional
    @Modifying
    @Query("""
            update Activity a set a.name = ?1, a.tel = ?2, a.address = ?3, a.ville = ?4, a.description = ?5
            where a.activityId = ?6""")
    void updateNameAndTelAndAddressAndVilleAndDescriptionByActivityId(String name, String tel, String address, String ville, String description, Long activityId);
    @Query("select a from Activity a where a.name like %?1%")
    List<Activity> findByNameLike(String name);
}
