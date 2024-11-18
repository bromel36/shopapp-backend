package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ApiMessage("create a product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.handleCreateProduct(product));
    }

    @PutMapping("/products")
    @ApiMessage("update a product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(this.productService.handleUpdateProduct(product));
    }

    @GetMapping("/products/{id}")
    @ApiMessage("fetch a product")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(productService.handleFetchProductById(id));
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
}
