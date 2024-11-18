package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.ICategoryService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    @ApiMessage("create a category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.handleCreateCategory(category));
    }

    @PutMapping("/categories")
    @ApiMessage("update a category")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.handleUpdateCategory(category));
    }

    @DeleteMapping("categories/{id}")
    @ApiMessage("delete a category")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {

        this.categoryService.handleDeleteCategory(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("categories/{id}")
    @ApiMessage("fetch a category")
    public ResponseEntity<Category> getCategory(@PathVariable("id") String id) {
        return ResponseEntity.ok(categoryService.handleFetchCategoryById(id));
    }

    @GetMapping("categories")
    @ApiMessage("fetch all categories")
    public ResponseEntity<PaginationResponseDTO> getCategory(
            @Filter Specification<Category> spec,
            Pageable pageable
    ) {
        PaginationResponseDTO paginationResponseDTO = this.categoryService.handleFetchAllCategories(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }




}
