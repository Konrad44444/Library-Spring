package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
