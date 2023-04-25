package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.UserCommand;
import database.project.library.model.User;
import lombok.Synchronized;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    private final LoanToLoanCommand toLoanCommandConverter;

    public UserToUserCommand(LoanToLoanCommand toLoanCommandConverter) {
        this.toLoanCommandConverter = toLoanCommandConverter;
    }


    @Synchronized
    @Override
    @Nullable
    public UserCommand convert(User source) {
        if(source == null)
            return null;

        UserCommand userCommand = new UserCommand();
        userCommand.setId(source.getId());
        userCommand.setLogin(source.getLogin());
        userCommand.setPassword(source.getPassword());
        userCommand.setFirstName(source.getFirstName());
        userCommand.setLastName(source.getLastName());
        userCommand.setIsLibrarian(source.getIsLibrarian());

        if(source.getLoans() != null && source.getLoans().size() > 0) {
            source.getLoans().forEach(
                loan -> userCommand.getLoans().add(toLoanCommandConverter.convert(loan))
            );
        }

        return userCommand;
    }
    
}
