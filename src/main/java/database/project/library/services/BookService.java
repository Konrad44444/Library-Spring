package database.project.library.services;

import java.util.List;

import database.project.library.model.Book;

public interface BookService {
    List<Book> getAllBooks();
}
