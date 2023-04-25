package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.LoanCommand;
import database.project.library.model.Loan;
import lombok.Synchronized;

@Component
public class LoanCommandToLoan implements Converter<LoanCommand, Loan> {
    private final UserCommandToUser toUserConverter;
    private final BookCommandToBook toBookConverter;

    public LoanCommandToLoan(UserCommandToUser toUserConverter, BookCommandToBook toBookConverter) {
        this.toUserConverter = toUserConverter;
        this.toBookConverter = toBookConverter;
    }


    @Synchronized
    @Override
    @Nullable
    public Loan convert(LoanCommand source) {
        if(source == null)
            return null;

        Loan loan = new Loan();
        loan.setId(source.getId());
        loan.setDateOfLoan(source.getDateOfLoan());
        loan.setDateOfReturn(source.getDateOfReturn());
        loan.setUser(toUserConverter.convert(source.getUser()));

        if(source.getBooks() != null && source.getBooks().size() > 0) {
            source.getBooks().forEach(
                book -> loan.getBooks().add(toBookConverter.convert(book))
            );
        }

        return loan;
    }
    
}
