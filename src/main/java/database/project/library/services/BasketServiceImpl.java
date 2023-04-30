package database.project.library.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.model.Basket;
import database.project.library.model.Book;
import database.project.library.model.User;
import database.project.library.repositories.BasketRepository;
import database.project.library.repositories.BookRepository;
import database.project.library.repositories.UserRepository;

@Service
public class BasketServiceImpl implements BasketService{
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoginService loginService;

    public BasketServiceImpl(BasketRepository basketRepository, BookRepository bookRepository, UserRepository userRepository, LoginService loginService) {
        this.basketRepository = basketRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loginService = loginService;
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
                        basket.setUser(user);
                        user.setBasket(basket);
                    } else {
                        basket = basketRepository.findById(user.getBasket().getId()).get();
                    }

                    // add book to basket, then save book and basket
                    basket.getBooks().add(book);
                    book.setBasket(basket);

                    userRepository.save(user);
                    bookRepository.save(book);
                    basketRepository.save(basket);

                } else
                    throw new RuntimeException(Util.NO_ACTIVE_USER);

            } else
                throw new RuntimeException(Util.NO_BOOK_FOUND);

        } else 
            throw new RuntimeException(Util.INVALID_ID);
        
    }

    
}
