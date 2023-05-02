package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import database.project.library.commands.BookCommand;
import database.project.library.converters.BookCommandToBook;
import database.project.library.converters.BookToBookCommand;
import database.project.library.model.Basket;
import database.project.library.model.Book;
import database.project.library.model.Loan;
import database.project.library.model.User;
import database.project.library.repositories.BasketRepository;
import database.project.library.repositories.BookRepository;
import database.project.library.repositories.LoanRepository;
import database.project.library.repositories.UserRepository;

@Service
public class BasketServiceImpl implements BasketService{
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final BookToBookCommand toBookCommand;
    private final BookCommandToBook toBook;
    private final LoanRepository loanRepository;

    public BasketServiceImpl(BasketRepository basketRepository, BookRepository bookRepository, UserRepository userRepository, LoginService loginService, BookToBookCommand toBookCommand, BookCommandToBook bookCommandToBook, LoanRepository loanRepository) {
        this.basketRepository = basketRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loginService = loginService;
        this.toBookCommand = toBookCommand;
        this.toBook = bookCommandToBook;
        this.loanRepository = loanRepository;
    }


    @Override
    public void addBookToBasket(String id) {
        if(Util.isNumeric(id)) {

            // get book from db -> change available status to booked
            Optional<Book> bookOptional = bookRepository.findById(Long.parseLong(id));

            if(bookOptional.isPresent()) {

                Book book = bookOptional.get();
                book.setAvailable(Util.BOOKED);

                // find active user and find out if it has basket
                // if not create new one related to this user

                Optional<User> userOptional = loginService.getCurrentUser();

                if(userOptional.isPresent()) {

                    User user = userOptional.get();

                    Basket basket;
                    if(user.getBasket() == null) {
                        basket = new Basket();
                        user.setBasket(basket);
                    } else {
                        basket = basketRepository.findById(user.getBasket().getId()).get();
                    }

                    // add book to basket, then save book and basket
                    basket.getBooks().add(book);
                    book.setBasket(basket);

                    basketRepository.save(basket);
                    userRepository.save(user);
                    bookRepository.save(book);

                } else
                    throw new RuntimeException(Util.NO_ACTIVE_USER);

            } else
                throw new RuntimeException(Util.NO_BOOK_FOUND);

        } else 
            throw new RuntimeException(Util.INVALID_ID);
        
    }


    @Override
    public List<BookCommand> getAllBooksFromBasket() {
        
        Optional<User> currentUser = loginService.getCurrentUser();

        if(currentUser.isPresent()) {
            // user is logged in -> find his basket id
            User user = currentUser.get();

            if(user.getBasket() != null) {
                // user has basket -> get basket id and list all books with this basket id

                List<BookCommand> list = new ArrayList<>();

                bookRepository.findAll().iterator().forEachRemaining(
                    book -> {

                        // book is in basket
                        if(book.getBasket() != null) {

                            //book is in right basket
                            if(book.getBasket().getId().equals(user.getBasket().getId())) 
                                list.add(toBookCommand.convert(book));
                        
                        }
                            
                    }
                );

                return list;

            } else 
                // user has no basket -> return empty list
                return new ArrayList<>();


        } else
            throw new RuntimeException(Util.NO_ACTIVE_USER);
    }


    @Override
    public void removeFromBasketById(String id) {

        if (Util.isNumeric(id)) {

            Optional<Book> bookOptional = bookRepository.findById(Long.parseLong(id));
            
            if (bookOptional.isPresent()) {
                // set book bakset id to null
                Book book = bookOptional.get();
                book.setBasket(null);
                book.setAvailable(Util.AVAILABLE);
                bookRepository.save(book);
                
            } else 
                throw new RuntimeException(Util.NO_BOOK_FOUND);
            
        } else 
            throw new RuntimeException(Util.INVALID_ID);
        
    }

    @Override
    public void makeLoan() {
        
        // get current user
        Optional<User> userOptional = loginService.getCurrentUser();
        if(userOptional.isPresent()) {

            User user = userOptional.get();

            if(user.getBasket() != null) {

                List<Book> books = getAllBooksFromBasket().stream()
                    .map(book -> toBook.convert(book))
                    .collect(Collectors.toList());

                Loan loan = new Loan(books, user);
                
                books.forEach(book -> {
                        book.setLoan(loan);
                        book.setAvailable(Util.UNAVAILABLE);
                    }
                );
                user.getLoans().add(loan);
                
                loanRepository.save(loan);
                bookRepository.saveAll(books);
                userRepository.save(user);

            } else
                throw new RuntimeException(Util.NO_BASKET);

        } else  
            throw new RuntimeException(Util.NO_ACTIVE_USER);
        
    }


}
