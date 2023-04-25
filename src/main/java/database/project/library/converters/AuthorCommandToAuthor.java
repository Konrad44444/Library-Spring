package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.AuthorCommand;
import database.project.library.model.Author;

import lombok.Synchronized;

@Component
public class AuthorCommandToAuthor implements Converter<AuthorCommand, Author> {

    @Synchronized
    @Override
    @Nullable
    public Author convert(AuthorCommand source) {
        if(source == null) 
            return null;

        final Author author = new Author();
        author.setId(source.getId());
        author.setFirstName(source.getFirstName());
        author.setLastName(source.getLastName());

        return author;
    }
    
}
