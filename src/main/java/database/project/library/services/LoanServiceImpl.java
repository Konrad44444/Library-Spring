package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import database.project.library.commands.LoanCommand;
import database.project.library.converters.LoanToLoanCommand;
import database.project.library.model.Book;
import database.project.library.model.Loan;
import database.project.library.model.User;
import database.project.library.repositories.BookRepository;
import database.project.library.repositories.LoanRepository;
import database.project.library.repositories.UserRepository;

@Service
public class LoanServiceImpl implements LoanService{
    
    private final LoanRepository loanRepository;
    private final LoanToLoanCommand toLoanCommand;
    private final LoginService loginService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(LoanRepository loanRepository, LoanToLoanCommand toLoanCommand, LoginService loginService, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.toLoanCommand = toLoanCommand;
        this.loginService = loginService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

    @Override
    public void returnLoanById(String id) {
        if(Util.isNumeric(id)) {

            Optional<Loan> loanOptional = loanRepository.findById(Long.parseLong(id));

            if(loanOptional.isPresent()) {

                // get user from loan, delete loan from user's list of loans, then delete loan from db
                Loan loan = loanOptional.get();
                User user = loan.getUser();

                user.getLoans().remove(loan);
                
                // set books status to 'Dostępna' and loan to null
                List<Book> books = loan.getBooks();

                books.forEach(
                    book -> {
                        book.setAvailable(Util.AVAILABLE);
                        book.setLoan(null);
                    }
                );

                // save changes to db, delete loan by id
                bookRepository.saveAll(books);
                userRepository.save(user);
                loanRepository.deleteById(Long.parseLong(id));  

            } else 
                throw new RuntimeException(Util.NO_LOAN_FOUND);

        } else
            throw new RuntimeException(Util.INVALID_ID);
        
    }

    
  
}
