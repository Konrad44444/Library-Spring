package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
