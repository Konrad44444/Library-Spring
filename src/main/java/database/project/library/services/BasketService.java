package database.project.library.services;

import java.util.List;

import database.project.library.commands.BookCommand;

public interface BasketService {
    void addBookToBasket(String id);
    List<BookCommand> getAllBooksFromBasket();
    void removeFromBasketById(String id);
    void makeLoan();
}
