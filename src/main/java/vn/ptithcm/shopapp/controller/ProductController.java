package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.request.ProductRequestDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ApiMessage("create a product")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.handleCreateProduct(requestDTO));
    }

    @PutMapping("/products")
    @ApiMessage("update a product")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody ProductRequestDTO product) {
        return ResponseEntity.ok().body(this.productService.handleUpdateProduct(product));
    }

    @GetMapping("/products/{id}")
    @ApiMessage("fetch a product")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(productService.handleFetchProductResponseById(id));
    }

    @GetMapping("/products")
    @ApiMessage("fetch all products")
    public ResponseEntity<PaginationResponseDTO> getAllProducts(
            @Filter Specification<Product> spec,
            Pageable pageable
    ) {
        PaginationResponseDTO paginationResponseDTO = productService.handleFetchAllProducts(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

    @GetMapping("/products/filter")
    @ApiMessage("get data for config filter")
    public ResponseEntity<Map<String, List<String>>> getFilter() {
        return ResponseEntity.ok(productService.getTop7DistinctProductFeatures());
    }
}
