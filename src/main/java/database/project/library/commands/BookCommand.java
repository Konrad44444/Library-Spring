package database.project.library.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookCommand {
    private Long id;
    private AuthorCommand author;
    private String title;
    private Boolean available;
    private List<CategoryCommand> categories = new ArrayList<>();
}
