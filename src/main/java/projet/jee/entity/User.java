package projet.jee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {

    private String username;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @OneToMany
    private List<Subscription> subscriptions;

    public User addSubscription(Subscription subscription){
        subscriptions.add(subscription);
        return this;
    }
}
