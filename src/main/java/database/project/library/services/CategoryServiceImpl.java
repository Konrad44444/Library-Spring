package database.project.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import database.project.library.model.Category;
import database.project.library.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(categories::add);

        return categories;
    }
    
}
