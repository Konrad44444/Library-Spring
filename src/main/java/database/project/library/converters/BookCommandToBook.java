package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.BookCommand;
import database.project.library.model.Book;
import lombok.Synchronized;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {
    private final AuthorCommandToAuthor toAuthorConverter;
    private final CategoryCommandToCategory toCategoryConverter;

    public BookCommandToBook(AuthorCommandToAuthor toAuthorConverter, CategoryCommandToCategory toCategoryConverter) {
        this.toAuthorConverter = toAuthorConverter;
        this.toCategoryConverter = toCategoryConverter;
    }

    @Synchronized
    @Override
    @Nullable
    public Book convert(BookCommand source) {
        if (source == null) 
            return null;

        Book book = new Book();
        book.setId(source.getId());
        book.setAuthor(toAuthorConverter.convert(source.getAuthor()));
        book.setTitle(source.getTitle());
        book.setAvailable(source.getAvailable());
        book.setCategory(toCategoryConverter.convert(source.getCategory()));

        return book;
    }
    
}
