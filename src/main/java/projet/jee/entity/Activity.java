package projet.jee.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String mail;
    private String ville;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activityId;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "subscriptionID.activity")
    private List<Subscription> subscriptions;

    private Float notationAverage;

    public Float getNotationAverage(){
        int compteur = 0;
        float sum = 0;
        for (Subscription s : subscriptions){
            Integer note = s.getNote();
            if (note != null){
                compteur++;
                sum += note;
            }
        }

        if (compteur > 0) {
            float na = sum / compteur;
            notationAverage = (float) ((int) (na * 10) /10); // *,* étoiles (1 chiffre après virgule)
        } else {
            notationAverage = null;
        }
        return notationAverage;
    }
}
