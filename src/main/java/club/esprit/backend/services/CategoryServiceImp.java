package club.esprit.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import club.esprit.backend.entities.Category;
import club.esprit.backend.exceptions.CategoryNotFoundException;
import club.esprit.backend.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements ICategory{
    private  CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category) {
return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
          return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
          return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategoryName(Long id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("CategoryLibrary with id " + id + " not found"));

        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryDescription(Long id, String newDescription) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("CategoryLibrary with id " + id + " not found"));

        category.setDescription(newDescription);
        return categoryRepository.save(category);
    }
}
