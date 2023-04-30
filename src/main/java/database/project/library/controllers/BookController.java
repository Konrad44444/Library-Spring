package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import database.project.library.commands.AuthorCommand;
import database.project.library.commands.BookCommand;
import database.project.library.commands.CategoryCommand;
import database.project.library.model.Author;
import database.project.library.model.Category;
import database.project.library.services.AuthorService;
import database.project.library.services.BookService;
import database.project.library.services.CategoryService;

@Controller
public class BookController {
    private static final String ADD_BOOK_MAP = "/addbook";
    private static final String ADD_BOOK_PATH = "/librarian/addbook";
    private static final String BOOK = "book";
    private static final String AUTHOR = "author";
    private static final String CATEGORY = "category";
    private static final String AUTHOR_LIST = "authorList";
    private static final String CATEGORY_LIST = "categoriesList";
    private static final String SAVE_MAP = "/save";
    private static final String MAIN_VIEW_REDIRECT = "redirect:/mainview";
    private static final String EDIT_BOOK_MAP = "/edit/book/{id}";
    private static final String EDIT_BOOK_PATH = "/librarian/editbook";
    private static final String EDIT = "/edit";

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @GetMapping(ADD_BOOK_MAP)
    public String addNewBook(Model model) {
        BookCommand bookCommand = new BookCommand();
        AuthorCommand authorCommand = new AuthorCommand();
        CategoryCommand categoryCommand = new CategoryCommand();

        model.addAttribute(BOOK, bookCommand);
        model.addAttribute(AUTHOR, authorCommand);
        model.addAttribute(CATEGORY, categoryCommand);

        List<Author> authorList = authorService.getAllAuthors();
        List<Category> categoryList = categoryService.getAllCategories();

        model.addAttribute(AUTHOR_LIST, authorList);
        model.addAttribute(CATEGORY_LIST, categoryList);


        return ADD_BOOK_PATH;
    }

    @PostMapping(SAVE_MAP)
    public String saveOrUpdate(
        @ModelAttribute BookCommand bookCommand,
        @ModelAttribute AuthorCommand authorCommand,
        @ModelAttribute CategoryCommand categoryCommand
    ) {

        bookService.saveNewBook(bookCommand, authorCommand, categoryCommand);

        return MAIN_VIEW_REDIRECT;
    }

    @GetMapping(EDIT_BOOK_MAP)
    public String editBook(@PathVariable String id, Model model) {

        BookCommand bookCommandFound = bookService.getBookComandById(id);

        model.addAttribute(BOOK, bookCommandFound);
        List<Author> authorList = authorService.getAllAuthors();
        List<Category> categoryList = categoryService.getAllCategories();

        model.addAttribute(AUTHOR_LIST, authorList);
        model.addAttribute(CATEGORY_LIST, categoryList);

        return EDIT_BOOK_PATH;
    }

    @PostMapping(EDIT)
    public String saveEditedBook(@ModelAttribute BookCommand bookCommand) {
        
        bookService.saveEditedBook(bookCommand);

        return MAIN_VIEW_REDIRECT;
    }
}
