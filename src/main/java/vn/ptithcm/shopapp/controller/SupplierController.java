package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Address;
import vn.ptithcm.shopapp.model.entity.Supplier;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.SupplierResponseDTO;
import vn.ptithcm.shopapp.service.ISupplierService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Supplier")
public class SupplierController {

    private final ISupplierService supplierService;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public SupplierController(ISupplierService supplierService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.supplierService = supplierService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @ApiMessage("create a supplier")
    @PostMapping("/suppliers")
    @Operation(summary = "Create a supplier", description = "Create a new supplier and return the created supplier details.")
    public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.handleCreateSupplier(supplier));
    }

    @ApiMessage("update a supplier")
    @PutMapping("/suppliers")
    @Operation(summary = "Update a supplier", description = "Update an existing supplier and return the updated supplier details.")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok().body(this.supplierService.handleUpdateSupplier(supplier));
    }

    @ApiMessage("get a supplier")
    @GetMapping("/suppliers/{id}")
    @Operation(summary = "Fetch a supplier", description = "Retrieve details of a supplier by their ID.")
    public ResponseEntity<SupplierResponseDTO> getSupplier(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.supplierService.handleFetchSupplierById(id));
    }

    @ApiMessage("delete a supplier")
    @DeleteMapping("/suppliers/{id}")
    @Operation(summary = "Delete a supplier", description = "Delete a supplier by their ID.")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        this.supplierService.handleDeleteSupplier(id);
        return ResponseEntity.ok().body(null);
    }

    @ApiMessage("get all suppliers")
    @GetMapping("/suppliers")
    @Operation(summary = "Fetch all suppliers", description = "Retrieve a paginated list of all suppliers with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllSuppliers(
            @Parameter(
                    description = "Filtering expression (e.g., id:'1')",
                    example = "id:'1'"
            )
            @RequestParam(name = "filter", required = false) String filter,

            @ParameterObject Pageable pageable) {
        Specification<Supplier> spec = filter == null ? Specification.where(null) : filterSpecificationConverter.convert(filterParser.parse(filter));
        PaginationResponseDTO responseDTO = supplierService.handleFetchAllSuppliers(spec, pageable);

        return ResponseEntity.ok().body(responseDTO);
    }

}
