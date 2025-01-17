package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.CategoryLibrary;
import club.esprit.backend.repository.libary.CategotyRepo;
import club.esprit.backend.services.libary.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategotyRepo categotyRepo;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategory() {
        CategoryLibrary category = new CategoryLibrary();
        when(categotyRepo.save(category)).thenReturn(category);

        CategoryLibrary result = categoryService.addCategory(category);

        assertEquals(category, result);
        verify(categotyRepo, times(1)).save(category);
    }

    @Test
    void testGetAll() {
        List<CategoryLibrary> categories = List.of(new CategoryLibrary());
        when(categotyRepo.findAll()).thenReturn(categories);

        List<CategoryLibrary> result = categoryService.getAll();

        assertEquals(categories, result);
        verify(categotyRepo, times(1)).findAll();
    }
}
