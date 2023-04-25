package database.project.library.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthorCommand {
    private Long id;
    private String firstName;
    private String lastName;
}
