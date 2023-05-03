package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.CategoryCommand;
import database.project.library.converters.CategoryToCategoryCommand;
import database.project.library.model.Category;
import database.project.library.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand toCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand toCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.toCategoryCommand = toCategoryCommand;
    }    


    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(categories::add);

        return categories;
    }

    @Override
    public CategoryCommand getCategoryCommandById(String id) {
        
        if(Util.isNumeric(id)) {
            Optional<Category> categoryOptional = categoryRepository.findById(Long.parseLong(id));

            if(categoryOptional.isPresent())
                return toCategoryCommand.convert(categoryOptional.get());
            else
                throw new RuntimeException(Util.NO_CATEGORY_FOUND);

        } else 
            throw new RuntimeException(Util.INVALID_ID);

    }

    @Override
    public void saveEditedCategoryCommand(CategoryCommand categoryCommand) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryCommand.getId());

        if(categoryOptional.isPresent()) {

            Category category = categoryOptional.get();
            category.setName(categoryCommand.getName());

            categoryRepository.save(category);

        } else 
            throw new RuntimeException(Util.NO_CATEGORY_FOUND);
        
    }

    
    
}
