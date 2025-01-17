package club.esprit.backend.services;

import club.esprit.backend.entities.Category;

import java.util.List;
import java.util.Optional;

public interface ICategory {
    Category saveCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    Optional<Category> getCategoryByName(String name);
    List<Category> getAllCategories();
    void deleteCategory(Long id);
    Category updateCategoryName(Long id, String newName);
    Category updateCategoryDescription(Long id, String newDescription);
}