package projet.jee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user") // Car user déjà pris par SQL
public class User {

    private String username;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @OneToMany
    private List<Subscription> subscriptions;

    public User addSubscription(Subscription subscription){
        subscriptions.add(subscription);
        return this;
    }
}
