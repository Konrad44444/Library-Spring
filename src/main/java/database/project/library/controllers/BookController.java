package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView saveOrUpdate(
        @ModelAttribute BookCommand bookCommand,
        @ModelAttribute AuthorCommand authorCommand,
        @ModelAttribute CategoryCommand categoryCommand
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(MAIN_VIEW_REDIRECT);

        // 
        // System.out.println(bookCommand.getTitle() + " " + bookCommand.getAuthor().getId() + " " + bookCommand.getCategory().getId());
        // System.out.println(authorCommand);
        // System.out.println(categoryCommand);

        bookService.saveOrUpade(bookCommand, authorCommand, categoryCommand);

        return modelAndView;
    }
}
