package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.LoanCommand;
import database.project.library.converters.LoanToLoanCommand;
import database.project.library.model.Loan;
import database.project.library.model.User;
import database.project.library.repositories.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService{
    
    private final LoanRepository loanRepository;
    private final LoanToLoanCommand toLoanCommand;
    private final LoginService loginService;

    public LoanServiceImpl(LoanRepository loanRepository, LoanToLoanCommand toLoanCommand, LoginService loginService) {
        this.loanRepository = loanRepository;
        this.toLoanCommand = toLoanCommand;
        this.loginService = loginService;
    }


    @Override
    public List<LoanCommand> getAllCurrentUserLoans() {

        // get current user
        Optional<User> userOptional = loginService.getCurrentUser();

        if(userOptional.isPresent()) {

            User user = userOptional.get();

            List<LoanCommand> loans = new ArrayList<>();

            if(user.getLoans().isEmpty()){
                // user has no loans -> return empty list
                return loans;
            }
            else {
                // user has loans -> map it into List<LoanCommand> and return

                user.getLoans().forEach(
                    loan -> loans.add(toLoanCommand.convert(loan))
                );

                return loans;
            }
            
        } else 
            throw new RuntimeException(Util.NO_ACTIVE_USER);

    }

    @Override
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();

        loanRepository.findAll().iterator()
            .forEachRemaining(loans::add);

        return loans;
    }

    
  
}
