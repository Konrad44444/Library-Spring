package database.project.library.services;

import java.util.List;

import database.project.library.commands.CategoryCommand;
import database.project.library.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    CategoryCommand getCategoryCommandById(String id);
    void saveEditedCategoryCommand(CategoryCommand categoryCommand);
}
