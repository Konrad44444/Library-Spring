package database.project.library.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoanCommand {
    private Long id;
    private List<BookCommand> books = new ArrayList<>();
    private UserCommand user;
    private LocalDate dateOfLoan;
    private LocalDate dateOfReturn;
}
