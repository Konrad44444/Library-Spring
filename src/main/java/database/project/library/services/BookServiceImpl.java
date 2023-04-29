package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.AuthorCommand;
import database.project.library.commands.BookCommand;
import database.project.library.commands.CategoryCommand;
import database.project.library.converters.AuthorCommandToAuthor;
import database.project.library.converters.BookCommandToBook;
import database.project.library.converters.CategoryCommandToCategory;
import database.project.library.model.Author;
import database.project.library.model.Book;
import database.project.library.model.Category;
import database.project.library.repositories.AuthorRepository;
import database.project.library.repositories.BookRepository;
import database.project.library.repositories.CategoryRepository;

@Service
public class BookServiceImpl implements BookService{
    private static final String EMPTY_STRING = "";
    private static final String NO_AUTHOR = "Author not found!";
    private static final String NO_CATEGORY = "Category not found!";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookCommandToBook toBook;
    private final AuthorCommandToAuthor toAuthor;
    private final CategoryCommandToCategory toCategory;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, BookCommandToBook toBook, AuthorCommandToAuthor toAuthor, CategoryCommandToCategory toCategory) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.toBook = toBook;
        this.toAuthor = toAuthor;
        this.toCategory = toCategory;
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }

    @Override
    public Book saveOrUpade(BookCommand bookCommand, AuthorCommand authorCommand, CategoryCommand categoryCommand) {
        // check wheather it is new author or category to save
        Author author;

        if(authorCommand.getFirstName().equals(EMPTY_STRING) && authorCommand.getLastName().equals(EMPTY_STRING)) {

            // both name and surname of author is empty -> use author from select
            Optional<Author> authorOptional = authorRepository.findById(bookCommand.getAuthor().getId());

            if(authorOptional.isPresent()) author = authorOptional.get();
            else throw new RuntimeException(NO_AUTHOR);

        } else {
            
            // name and surname aren't empty -> create new author
            author = authorRepository.save(toAuthor.convert(authorCommand));

        }

        Category category;
        if(categoryCommand.getName().equals(EMPTY_STRING)) {

            // category name is empty -> use category from select
            Optional<Category> categoryOptional = categoryRepository.findById(bookCommand.getCategory().getId());

            if(categoryOptional.isPresent()) category = categoryOptional.get();
            else throw new RuntimeException(NO_CATEGORY);

        } else {

            // category name isn't empty -> create new category
            category = categoryRepository.save(toCategory.convert(categoryCommand));

        }

        Book book = toBook.convert(bookCommand);
        book.setAvailable(true);
        book.setAuthor(author);
        book.setCategory(category);

        return bookRepository.save(book);
    }
    
}
