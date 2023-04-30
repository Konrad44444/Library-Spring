package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.model.Author;
import database.project.library.services.AuthorService;

@Controller
public class AuthorController {
    private static final String AUTHORS_MAP = "/authors";
    private static final String AUTHORS_LIST = "authorsList";
    private static final String AUTHORS_PATH = "/librarian/authors";
    
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(AUTHORS_MAP)
    public String getAllAuthors(Model model) {

        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute(AUTHORS_LIST, authors);

        return AUTHORS_PATH;
    }

}
