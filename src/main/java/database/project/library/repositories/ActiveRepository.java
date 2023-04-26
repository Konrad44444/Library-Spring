package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Active;

public interface ActiveRepository extends CrudRepository<Active, Long> {
    
}
