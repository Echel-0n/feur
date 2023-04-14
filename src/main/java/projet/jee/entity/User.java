package projet.jee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user") // Car user déjà pris par SQL
public class User {

    private String password;

    @Column(unique = true)
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private boolean admin;
}
