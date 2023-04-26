package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.BasketCommand;
import database.project.library.model.Basket;
import lombok.Synchronized;

@Component
public class BasketCommandToBasket implements Converter <BasketCommand, Basket> {
    private final BookCommandToBook toBook;

    public BasketCommandToBasket(BookCommandToBook toBook) {
        this.toBook = toBook;
    }

    @Synchronized
    @Override
    @Nullable
    public Basket convert(BasketCommand source) {
        if(source == null)
            return null;
        
        Basket basket = new Basket();
        basket.setId(source.getId());
        
        if(source.getBooks() != null && !source.getBooks().isEmpty()) {
            source.getBooks().forEach(
                book -> basket.getBooks().add(toBook.convert(book))
            );
        }

        return basket;
    }


    
}
