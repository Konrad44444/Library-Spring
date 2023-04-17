package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    
}
