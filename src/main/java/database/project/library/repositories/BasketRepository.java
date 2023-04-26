package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Basket;

public interface BasketRepository extends CrudRepository<Basket, Long> {
    
}
