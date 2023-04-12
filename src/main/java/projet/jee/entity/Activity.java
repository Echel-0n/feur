package projet.jee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
