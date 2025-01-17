package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.CategoryLibrary;

import java.util.List;

public interface ICategoryService {
    CategoryLibrary addCategory(CategoryLibrary category);
    List<CategoryLibrary> getAll();
}
