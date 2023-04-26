package database.project.library.commands;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class BasketCommand {
    private Long id;
    private List<BookCommand> books;
}
