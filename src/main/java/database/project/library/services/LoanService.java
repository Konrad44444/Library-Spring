package database.project.library.services;

import java.util.List;

import database.project.library.commands.LoanCommand;
import database.project.library.model.Loan;

public interface LoanService {
    List<LoanCommand> getAllCurrentUserLoans();
    List<Loan> getAllLoans();
    void returnLoanById(String id);
}
