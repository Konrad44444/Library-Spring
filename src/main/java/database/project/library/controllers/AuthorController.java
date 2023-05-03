package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import database.project.library.commands.AuthorCommand;
import database.project.library.model.Author;
import database.project.library.services.AuthorService;

@Controller
public class AuthorController {
    private static final String AUTHORS_MAP = "/authors";
    private static final String AUTHORS_LIST = "authorsList";
    private static final String AUTHORS_PATH = "/librarian/authors";
    private static final String EDIT_AUTHOR_MAP = "/edit/author/{id}";
    private static final String REDIRECT_AUTHORS_MAP = "redirect:" + AUTHORS_MAP;
    private static final String AUTHOR = "author";
    private static final String EDIT_AUTHOR_PATH = "/librarian/editauthor";
    private static final String SAVE_EDITED_MAP = "/editauthor";
    
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

    @GetMapping(EDIT_AUTHOR_MAP)
    public String editAuthor(@PathVariable String id, Model model) {

        model.addAttribute(AUTHOR, authorService.getAuthorCommandById(id));

        return EDIT_AUTHOR_PATH;
    }

    @PostMapping(SAVE_EDITED_MAP)
    public String saveEditedAuthor(@ModelAttribute AuthorCommand authorCommand, Model model) {

        authorService.saveEditedAuthorCommand(authorCommand);

        return REDIRECT_AUTHORS_MAP;
    }

}
