package vn.ptithcm.shopapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
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
            Specification<Supplier> spec,
            Pageable pageable) {
        PaginationResponseDTO responseDTO = supplierService.handleFetchAllSuppliers(spec, pageable);

        return ResponseEntity.ok().body(responseDTO);
    }

}
