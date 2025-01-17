package club.esprit.backend.controllers;

import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.jwt.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import club.esprit.backend.services.ICategory;
import club.esprit.backend.entities.Category;

import java.util.List;

@RestController
@RequestMapping("/api/category")
//@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final ICategory categoryService;
    private UserServiceImpl userService;

    @Autowired
    public CategoryController(ICategory categoryService, UserServiceImpl userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findUserByEmail(principal.getUsername());

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only admins can create categories");
        }

        category.setUser(user);
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.of(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Category> updateCategoryName(@PathVariable Long id, @RequestBody String newName) {
        return ResponseEntity.ok(categoryService.updateCategoryName(id, newName));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<Category> updateCategoryDescription(@PathVariable Long id, @RequestBody String newDescription) {
        return ResponseEntity.ok(categoryService.updateCategoryDescription(id, newDescription));
    }
}