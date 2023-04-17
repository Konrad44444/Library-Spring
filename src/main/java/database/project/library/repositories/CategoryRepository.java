package database.project.library.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
