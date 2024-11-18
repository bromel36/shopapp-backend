package vn.ptithcm.shopapp.controller;

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
public class SupplierController {

    private final ISupplierService supplierService;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @ApiMessage("create a supplier")
    @PostMapping("/suppliers")
    public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.supplierService.handleCreateSupplier(supplier));
    }

    @ApiMessage("update a supplier")
    @PutMapping("/suppliers")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok().body(this.supplierService.handleUpdateSupplier(supplier));
    }

    @ApiMessage("get a supplier")
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplier(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.supplierService.handleFetchSupplierById(id));
    }

    @ApiMessage("delete a supplier")
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") String id) {
        this.supplierService.handleDeleteSupplier(id);
        return ResponseEntity.ok().body(null);
    }

    @ApiMessage("get all suppliers")
    @GetMapping("/suppliers")
    public ResponseEntity<PaginationResponseDTO> getAllSuppliers(
            Specification<Supplier> spec,
            Pageable pageable
    ) {
        PaginationResponseDTO responseDTO = supplierService.handleFetchAllSuppliers(spec, pageable);

        return ResponseEntity.ok().body(responseDTO);
    }



}
