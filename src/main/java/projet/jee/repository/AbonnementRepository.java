package projet.jee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.jee.entity.Abonnement;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {
}
