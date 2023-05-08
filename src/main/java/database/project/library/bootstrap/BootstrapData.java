package database.project.library.bootstrap;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import database.project.library.model.Author;
import database.project.library.model.Book;
import database.project.library.model.Category;
import database.project.library.model.User;
import database.project.library.repositories.ActiveRepository;
import database.project.library.repositories.AuthorRepository;
import database.project.library.repositories.BasketRepository;
import database.project.library.repositories.BookRepository;
import database.project.library.repositories.CategoryRepository;
import database.project.library.repositories.LoanRepository;
import database.project.library.repositories.UserRepository;

@Component
public class BootstrapData implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ActiveRepository activeRepository;
    private final LoanRepository loanRepository;

    public BootstrapData(CategoryRepository categoryRepository, AuthorRepository authorRepository, BookRepository bookRepository, BasketRepository basketRepository, UserRepository userRepository, ActiveRepository activeRepository, LoanRepository loanRepository) {
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.activeRepository = activeRepository;
        this.loanRepository = loanRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        //deleting and saving data for debug purposes
        // basketRepository.deleteAll();
        // bookRepository.deleteAll();
        // authorRepository.deleteAll();
        // userRepository.deleteAll();
        // activeRepository.deleteAll();
        // //loanRepository.deleteAll();
        // loadBooksAndAuthors();
        // loadUsers();

        System.out.println("--- Books, Authors and Users data loaded. ---");
        
    }

    private void loadBooksAndAuthors() {
        Optional<Category> classicOptional = categoryRepository.findByName("Literatura klasyczna");

        if(!classicOptional.isPresent()) throw new RuntimeErrorException(null, "Category doesn't exist");
        Category classic = classicOptional.get();
        


        Author GO = new Author("George", "Orwell");
        Book FZ = new Book(GO, "Folwark zwierzecy", "Dostepna", classic);

        authorRepository.save(GO);
        bookRepository.save(FZ);

        Author KK = new Author("Ken", "Kesey");
        Book LNKG = new Book(KK, "Lot nad kukulczym gniazdem", "Dostepna", classic);

        authorRepository.save(KK);
        bookRepository.save(LNKG);

        Book R1984 = new Book(GO, "Rok 1984", "Dostepna", classic);
        bookRepository.save(R1984);

        System.out.println("--- Books and Authors data loading... ---");
    }

    private void loadUsers() {
        User bulbek = new User("bulbek", "Kwakwa5!", "Bulbulezar", "Ptak", true);
        bulbek.setId(null);
        bulbek.setBasket(null);

        User konrad = new User("konrad", "konrad", "Konrad", "Warzecha", false);
        konrad.setId(null);
        konrad.setBasket(null);

        User mama = new User("mama", "mama", "Renata", "Warzecha", false);
        mama.setId(null);
        mama.setBasket(null);

        userRepository.save(bulbek);
        userRepository.save(konrad);
        userRepository.save(mama);

        System.out.println("--- Users data loading... ---");
    }
}
