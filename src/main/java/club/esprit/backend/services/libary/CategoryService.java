package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.CategoryLibrary;
import club.esprit.backend.repository.libary.CategotyRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService{
    private CategotyRepo categotyRepo;
    @Override
    public CategoryLibrary addCategory(CategoryLibrary category) {
        return categotyRepo.save(category);
    }

    @Override
    public List<CategoryLibrary> getAll() {
        return categotyRepo.findAll();
    }
}
