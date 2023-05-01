package database.project.library.converters;

import java.util.ArrayList;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.CategoryCommand;
import database.project.library.model.Category;
import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Override
    @Nullable
    public Category convert(CategoryCommand source) {
        if(source == null) 
            return null;

        Category category = new Category();
        category.setId(source.getId());
        category.setName(source.getName());
        category.setBooks(new ArrayList<>());

        return category;
    }

    
}
