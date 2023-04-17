package database.project.library.repositories;

import org.springframework.data.repository.CrudRepository;

import database.project.library.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    
}
