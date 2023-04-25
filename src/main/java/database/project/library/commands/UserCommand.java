package database.project.library.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserCommand {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isLibrarian;
    private List<LoanCommand> loans = new ArrayList<>();
}
