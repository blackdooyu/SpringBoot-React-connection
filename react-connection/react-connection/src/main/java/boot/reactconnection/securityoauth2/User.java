package boot.reactconnection.securityoauth2;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
    private String picture;
    private String role;

    public User(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role ="ROLE_USER";
    }

    protected User() {
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }
}
