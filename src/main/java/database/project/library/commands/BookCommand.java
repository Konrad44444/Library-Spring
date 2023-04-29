package database.project.library.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookCommand {
    private Long id;
    private AuthorCommand author = new AuthorCommand();
    private String title;
    private Boolean available;
    private CategoryCommand category = new CategoryCommand();

    @Override
    public String toString() {
        return this.title + " " + this.author.getFirstName() + " " + this.author.getLastName() + " " + this.category;
    }

    
}
