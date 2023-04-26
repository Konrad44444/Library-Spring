package database.project.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import database.project.library.model.Book;
import database.project.library.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(books::add);

        return books;
    }
    
}
