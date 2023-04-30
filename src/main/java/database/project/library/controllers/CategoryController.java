package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.model.Category;
import database.project.library.services.CategoryService;

@Controller
public class CategoryController {
    private static final String CATEGORIES_MAP = "/categories";
    private static final String CATEGORIES_LIST = "categoriesList";
    private static final String CATEGORIES_PATH = "/librarian/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(CATEGORIES_MAP)
    public String getAllCategories(Model model) {
        
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute(CATEGORIES_LIST, categories);

        return CATEGORIES_PATH;
    }
}
