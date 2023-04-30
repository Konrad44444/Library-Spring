package database.project.library.services;

import java.util.List;

import database.project.library.commands.AuthorCommand;
import database.project.library.commands.BookCommand;
import database.project.library.commands.CategoryCommand;
import database.project.library.model.Book;

public interface BookService {
    List<Book> getAllBooks();
    Book saveNewBook(BookCommand bookCommand, AuthorCommand authorCommand, CategoryCommand categoryCommand);
    BookCommand getBookComandById(String id);
    Book saveEditedBook(BookCommand bookCommand);
    void deleteBookById(String id);
}
