package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.request.ProductRequestDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;

import java.util.List;

public interface IProductService {
    ProductResponseDTO handleCreateProduct(ProductRequestDTO product);

    ProductResponseDTO handleUpdateProduct(ProductRequestDTO product);

    Product handleFetchProductById(String id);

    PaginationResponseDTO handleFetchAllProducts(Specification<Product> spec, Pageable pageable);

    List<Product> handleFetchAllProductByIds(List<String> ids);

    ProductResponseDTO handleFetchProductResponseById(String id);
}
