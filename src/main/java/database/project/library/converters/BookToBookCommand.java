package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.BookCommand;
import database.project.library.model.Book;
import lombok.Synchronized;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {
    private final AuthorToAuthorCommand toAuthorCommand;
    private final CategoryToCategoryCommand toCategoryCommand;

    public BookToBookCommand(AuthorToAuthorCommand toAuthorCommand, CategoryToCategoryCommand toCategoryCommand) {
        this.toAuthorCommand = toAuthorCommand;
        this.toCategoryCommand = toCategoryCommand;
    }


    @Synchronized
    @Override
    @Nullable
    public BookCommand convert(Book source) {
        if(source == null)
            return null;

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(source.getId());
        bookCommand.setAuthor(toAuthorCommand.convert(source.getAuthor()));
        bookCommand.setTitle(source.getTitle());
        bookCommand.setAvailable(source.getAvailable());
        bookCommand.setCategory(toCategoryCommand.convert(source.getCategory()));

        return bookCommand;
    }
    
}
