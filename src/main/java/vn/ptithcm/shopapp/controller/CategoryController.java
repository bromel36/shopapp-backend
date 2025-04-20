package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.ICategoryService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Category")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    @ApiMessage("create a category")
    @Operation(summary = "Create a category", description = "Create a new category and return the created category details.")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.handleCreateCategory(category));
    }

    @PutMapping("/categories")
    @ApiMessage("update a category")
    @Operation(summary = "Update a category", description = "Update an existing category and return the updated category details.")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.handleUpdateCategory(category));
    }

    @DeleteMapping("categories/{id}")
    @ApiMessage("delete a category")
    @Operation(summary = "Delete a category", description = "Delete a category by its ID.")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {

        this.categoryService.handleDeleteCategory(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("categories/{id}")
    @ApiMessage("fetch a category")
    @Operation(summary = "Fetch a category", description = "Fetch details of a category by its ID.")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.handleFetchCategoryById(id));
    }

    @GetMapping("categories")
    @ApiMessage("fetch all categories")
    @Operation(summary = "Fetch all categories", description = "Fetch a paginated list of all categories with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllCategory(
            @Filter Specification<Category> spec,
            Pageable pageable) {
        PaginationResponseDTO paginationResponseDTO = this.categoryService.handleFetchAllCategories(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }

}
