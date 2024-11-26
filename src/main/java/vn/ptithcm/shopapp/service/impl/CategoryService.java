package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.CategoryRepository;
import vn.ptithcm.shopapp.service.ICategoryService;
import vn.ptithcm.shopapp.util.PaginationUtil;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category handleCreateCategory(Category category) {
        if (isExistsCode(category.getCode())) {
            throw new IdInvalidException("Cate with code " + category.getCode() + " already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category handleUpdateCategory(Category category) {
        Category categoryDB = handleFetchCategoryById(category.getId());

        if (!category.getCode().equals(categoryDB.getCode()) && isExistsCode(category.getCode())) {
            throw new IdInvalidException("Category with code " + category.getCode() + " already exists");
        }
        categoryDB.setCode(category.getCode());
        categoryDB.setName(category.getName());

        return categoryRepository.save(categoryDB);
    }

    @Override
    public void handleDeleteCategory(Long id) {
        Category category = handleFetchCategoryById(id);

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new IdInvalidException("Category with id " + id + " has already products");
        }

        categoryRepository.delete(category);
    }

    public boolean isExistsCode(String code) {
        return this.categoryRepository.existsByCode(code);
    }

    public Category handleFetchCategoryById(Long id) {
        Category categoryDB = categoryRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Category with id " + id + " does not exist"));

        return categoryDB;
    }

    @Override
    public PaginationResponseDTO handleFetchAllCategories(Specification<Category> spec, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, categoryPage);

        result.setResult(categoryPage.getContent());

        return result;
    }
}
