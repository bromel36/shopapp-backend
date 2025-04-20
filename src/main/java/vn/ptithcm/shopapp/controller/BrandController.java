package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IBrandService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Brand")
public class BrandController {
    private final IBrandService brandService;

    public BrandController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brands")
    @ApiMessage("create a brand")
    @Operation(summary = "Create a brand", description = "Create a new brand and return the created brand details.")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.handleCreateBrand(brand));
    }

    @PutMapping("/brands")
    @ApiMessage("update a category")
    @Operation(summary = "Update a brand", description = "Update an existing brand and return the updated brand details.")
    public ResponseEntity<Category> updateBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok().body(brandService.handleUpdateBrand(brand));
    }

    @GetMapping("brands/{id}")
    @ApiMessage("fetch a brand")
    @Operation(summary = "Fetch a brand", description = "Fetch details of a brand by its ID.")
    public ResponseEntity<Brand> getBrand(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandService.handleFetchBrandById(id));
    }

    @GetMapping("/brands")
    @ApiMessage("fetch all brands")
    @Operation(summary = "Fetch all brands", description = "Fetch a paginated list of all brands with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllBrand(
            @Filter Specification<Brand> spec,
            Pageable pageable) {
        PaginationResponseDTO paginationResponseDTO = this.brandService.handldeFetchAllBrands(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }

}
