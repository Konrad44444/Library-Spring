package database.project.library.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import database.project.library.commands.CategoryCommand;
import database.project.library.model.Category;
import database.project.library.services.CategoryService;

@Controller
public class CategoryController {
    private static final String CATEGORIES_MAP = "/categories";
    private static final String CATEGORIES_LIST = "categoriesList";
    private static final String CATEGORIES_PATH = "/librarian/categories";
    private static final String EDIT_CATEGORY_MAP = "/edit/category/{id}";
    private static final String SAVE_EDITED_CATEGORY_MAP = "/editcategory";
    private static final String EDIT_CATEGORY_PATH = "/librarian/editcategory";
    private static final String CATEGORY = "category";
    private static final String REDIRECT_CATEGORIES_MAP = "redirect:" + CATEGORIES_MAP;

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

    @GetMapping(EDIT_CATEGORY_MAP)
    public String editCategory(@PathVariable String id, Model model) {

        model.addAttribute(CATEGORY, categoryService.getCategoryCommandById(id));

        return EDIT_CATEGORY_PATH;
    }

    @PostMapping(SAVE_EDITED_CATEGORY_MAP)
    public String saveEditedCategory(@ModelAttribute CategoryCommand categoryCommand, Model model) {

        categoryService.saveEditedCategoryCommand(categoryCommand);

        return REDIRECT_CATEGORIES_MAP;
    }
}
