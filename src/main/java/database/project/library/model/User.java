package database.project.library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String login;
    private String password;

    private String firstName;
    private String lastName;

    private Boolean isLibrarian;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();

    public User(String login, String password, String firstName, String lastName, Boolean isLibrarian) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLibrarian = isLibrarian;
    }
}
