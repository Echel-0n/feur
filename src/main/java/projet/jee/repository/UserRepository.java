package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projet.jee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
