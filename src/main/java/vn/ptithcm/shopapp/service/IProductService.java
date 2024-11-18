package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

import java.util.List;

public interface IProductService {
    Product handleCreateProduct(Product product);

    Product handleUpdateProduct(Product product);

    Product handleFetchProductById(String id);

    PaginationResponseDTO handleFetchAllProducts(Specification<Product> spec, Pageable pageable);

    List<Product> handleFetchAllProductByIds(List<String> ids);
}
