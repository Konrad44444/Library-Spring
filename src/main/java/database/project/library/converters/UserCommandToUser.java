package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.UserCommand;
import database.project.library.model.User;
import lombok.Synchronized;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {
    private final LoanCommandToLoan toLoanConverter;

    public UserCommandToUser(LoanCommandToLoan toLoanConverter) {
        this.toLoanConverter = toLoanConverter;
    }


    @Synchronized
    @Override
    @Nullable
    public User convert(UserCommand source) {
        if(source == null)
            return null;

        User user = new User();
        user.setId(source.getId());
        user.setLogin(source.getLogin());
        user.setPassword(source.getPassword());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setIsLibrarian(source.getIsLibrarian());

        if(source.getLoans() != null && source.getLoans().size() > 0) {
            source.getLoans().forEach(loan -> user.getLoans().add(toLoanConverter.convert(loan)));
        }

        return user;
    }
    
    
}
