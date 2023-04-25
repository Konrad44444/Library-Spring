package database.project.library.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import database.project.library.commands.CategoryCommand;
import database.project.library.model.Category;
import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Override
    @Nullable
    public CategoryCommand convert(Category source) {
        if(source == null)
            return null;

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setName(source.getName());

        return categoryCommand;
    }
    
}
