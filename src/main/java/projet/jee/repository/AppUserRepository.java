package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projet.jee.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

}
