package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.jee.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
