package projet.jee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Subscription {
    @EmbeddedId
    private SubscriptionID subscriptionID;

    private Integer note;
}