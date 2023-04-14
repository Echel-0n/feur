package projet.jee.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Setter
public class Subscription {
    @EmbeddedId
    private ID subscriptionID;

    private Integer note;

    @JsonGetter
    public Long getUser_id(){
        return subscriptionID.getUser().getUserId();
    }
    @JsonGetter
    public Long getActivity_id(){
        return subscriptionID.getActivity().getActivityId();
    }
    public Integer getNote(){
        return this.note;
    }

    @JsonIgnore
    public Activity getActivity(){
        return subscriptionID.getActivity();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class ID implements Serializable {
        @ManyToOne
        private User user;

        @ManyToOne
        private Activity activity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Shadow {
        private Long user;
        private Long activity;
        private Integer note;
    }
}