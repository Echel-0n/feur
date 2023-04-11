package projet.jee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import projet.jee.repository.AbonnementRepository;
import projet.jee.repository.ActivityRepository;
import projet.jee.repository.AppUserRepository;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Abonnement {

//    @Autowired
//    private ActivityRepository activityRepository;
//    @Autowired
//    private AppUserRepository appUserRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long abonneId;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "userId")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "activity", referencedColumnName = "activityId")
    private Activity activity;

//    public Abonnement(Long user, Long activity) {
//        this.user = appUserRepository.findById(user).get();
//        this.activity = activityRepository.findById(activity).get();
//    }
}
