package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import database.project.library.commands.BookCommand;
import database.project.library.services.BasketService;

@Controller
public class BasketController {
    private static final String ADD_TO_BASKET_MAP = "/addtobasket/{id}";
    private static final String MAIN_VIEW_REDIRECT = "redirect:/mainview";
    private static final String SHOW_BASKET_MAP = "/showbasket";
    private static final String BASKET_PATH = "/user/basket";
    private static final String BOOKS = "books";
    private static final String MESSAGE = "message";
    private static final String EMPTY_BASKET = "Twój koszyk jest pusty.";

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @GetMapping(ADD_TO_BASKET_MAP)
    public String addToBasket(@PathVariable String id, Model model) {

        basketService.addBookToBasket(id); 

        return MAIN_VIEW_REDIRECT;
    }

    @GetMapping(SHOW_BASKET_MAP)
    public String showBasket(Model model) {

        List<BookCommand> books = basketService.getAllBooksFromBasket();

        if(books.isEmpty()) 
            model.addAttribute(MESSAGE, EMPTY_BASKET);

        model.addAttribute(BOOKS, books);

        return BASKET_PATH;
    }
}
