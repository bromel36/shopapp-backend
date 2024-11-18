package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface ICategoryService {
    Category handleCreateCategory(Category category);

    Category handleUpdateCategory(Category category);

    void handleDeleteCategory(String id);

    Category handleFetchCategoryById(String id);

    PaginationResponseDTO handleFetchAllCategories(Specification<Category> spec, Pageable pageable);
}
