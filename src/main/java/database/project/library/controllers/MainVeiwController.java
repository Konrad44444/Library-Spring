package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.converters.UserToUserCommand;
import database.project.library.model.Book;
import database.project.library.model.User;
import database.project.library.services.BookService;
import database.project.library.services.LoginService;

@Controller
public class MainVeiwController {
    private static final String BOOKS = "books";
    private static final String LIBRARIAN_VIEW_PATH = "/librarian/mainview";
    private static final String USER_VIEW_PATH = "/user/mainview";

    private final BookService bookService;
    private final LoginService loginService;
    private final UserToUserCommand toUserCommand;

    public MainVeiwController(BookService bookService, LoginService loginService, UserToUserCommand toUserCommand) {
        this.bookService = bookService;
        this.loginService = loginService;
        this.toUserCommand = toUserCommand;
    }

    
    @GetMapping("/mainview")
    public String getMainView(Model model) {
        // pass list of books to view
        List<Book> books = bookService.getAllBooks();
        model.addAttribute(BOOKS, books);
        
        // get current user
        User user = loginService.getCurrentUser().get();

        // check if user is librarian or not and return proper view
        Boolean userIsLibrarian = loginService.checkIfUserIsLibrarian(toUserCommand.convert(user));

        if(Boolean.TRUE.equals(userIsLibrarian)) return LIBRARIAN_VIEW_PATH;
        else return USER_VIEW_PATH;
    }
}
