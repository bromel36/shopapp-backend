package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Address;
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
@Tag(name = "Product")
public class ProductController {

    private final IProductService productService;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public ProductController(IProductService productService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.productService = productService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @PostMapping("/products")
    @ApiMessage("create a product")
    @Operation(summary = "Create a product", description = "Create a new product and return the created product details.")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.handleCreateProduct(requestDTO));
    }

    @PutMapping("/products")
    @ApiMessage("update a product")
    @Operation(summary = "Update a product", description = "Update an existing product and return the updated product details.")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody ProductRequestDTO product) {
        return ResponseEntity.ok().body(this.productService.handleUpdateProduct(product));
    }

    @GetMapping("/products/{id}")
    @ApiMessage("fetch a product")
    @Operation(summary = "Fetch a product", description = "Retrieve details of a product by its ID.")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(productService.handleFetchProductResponseById(id));
    }

    @GetMapping("/products")
    @ApiMessage("fetch all products")
    @Operation(summary = "Fetch all products", description = "Retrieve a paginated list of all products with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllProducts(
            @Parameter(
                    description = "Filtering expression (e.g., id:'1')",
                    example = "id:'1'"
            )
            @RequestParam(name = "filter", required = false) String filter,

            @ParameterObject Pageable pageable) {
        Specification<Product> spec = filter == null ? null : filterSpecificationConverter.convert(filterParser.parse(filter));
        PaginationResponseDTO paginationResponseDTO = productService.handleFetchAllProducts(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

    @GetMapping("/products/filter")
    @ApiMessage("get data for config filter")
    @Operation(summary = "Get filter data", description = "Retrieve data for configuring product filters.")
    public ResponseEntity<Map<String, List<String>>> getFilter() {
        return ResponseEntity.ok(productService.getTop7DistinctProductFeatures());
    }
}
