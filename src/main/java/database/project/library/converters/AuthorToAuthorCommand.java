package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.AuthorCommand;
import database.project.library.model.Author;
import lombok.Synchronized;

@Component
public class AuthorToAuthorCommand implements Converter<Author, AuthorCommand> {

    @Synchronized
    @Override
    @Nullable
    public AuthorCommand convert(Author source) {
        if(source == null)
            return null;

        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(source.getId());
        authorCommand.setFirstName(source.getFirstName());
        authorCommand.setLastName(source.getLastName());

        return authorCommand;
    }
    
}
