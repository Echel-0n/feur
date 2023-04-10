package projet.jee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppUser {

    private String username;
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;



    public AppUser() {}

    public String getUsername(){
        return username;
    }

    public void setUsername(String u){
        this.username = u;
    }


    public String getPassword(){
        return password;
    }

    public void setPassword(String u){
        this.password = u;
    }

    public Long getUsernameId(){
        return userId;
    }

    public void setUsernameId(Long u) {
        this.userId = u;
    }
}
