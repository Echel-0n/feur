package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projet.jee.entity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    @Query("select a from Activity a where a.name like %?1%")
    List<Activity> findByNameLike(String name);
}
