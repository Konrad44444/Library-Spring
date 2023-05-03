package database.project.library.services;

import java.util.List;

import database.project.library.commands.LoanCommand;

public interface LoanService {
    List<LoanCommand> getAllCurrentUserLoans();
}
