package projet.jee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private String name;
    private String tel;
    private String address;
    private String ville;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activityId;

    @OneToMany(mappedBy = "activity")
    private List<Abonnement> abonne;
}
