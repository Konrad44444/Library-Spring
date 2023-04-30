package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import database.project.library.services.BasketService;

@Controller
public class BasketController {
    private static final String ADD_TO_BASKET_MAP = "/addtobasket/{id}";
    private static final String MAIN_VIEW_REDIRECT = "redirect:/mainview";

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @GetMapping(ADD_TO_BASKET_MAP)
    public String addToBasket(@PathVariable String id, Model model) {

        basketService.addBookToBasket(id); 

        return MAIN_VIEW_REDIRECT;
    }
}
