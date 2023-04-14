package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projet.jee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.username = ?1, u.password = ?2 where u.userId = ?3")
    void updateUsernameAndPasswordByUserId(String username, String password, Long userId);
    User getByUsername(String username);
}
