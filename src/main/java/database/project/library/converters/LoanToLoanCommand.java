package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.LoanCommand;
import database.project.library.model.Loan;
import lombok.Synchronized;

@Component
public class LoanToLoanCommand implements Converter<Loan, LoanCommand> {
    private final BookToBookCommand toBookCommand;

    public LoanToLoanCommand(BookToBookCommand toBookCommand) {
        this.toBookCommand = toBookCommand;
    }


    @Synchronized
    @Override
    @Nullable
    public LoanCommand convert(Loan source) {
        if(source == null)
            return null;
        
        LoanCommand loanCommand = new LoanCommand();
        loanCommand.setId(source.getId());
        loanCommand.setDateOfLoan(source.getDateOfLoan());
        loanCommand.setDateOfReturn(source.getDateOfReturn());

        if(source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks().forEach(
                book -> loanCommand.getBooks().add(toBookCommand.convert(book))
            );
        }

        return loanCommand;
    }
    
}
