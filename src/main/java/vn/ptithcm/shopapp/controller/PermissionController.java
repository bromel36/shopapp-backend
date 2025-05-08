package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IPermissionService;
import vn.ptithcm.shopapp.service.impl.PermissionService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Permission")
public class PermissionController {

    private final IPermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiMessage("create a permission")
    @PostMapping("/permissions")
    @Operation(summary = "Create a permission", description = "Create a new permission and return the created permission details.")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.permissionService.handldeCreatePermission(permission));
    }

    @ApiMessage("update a permission")
    @PutMapping("/permissions")
    @Operation(summary = "Update a permission", description = "Update an existing permission and return the updated permission details.")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission) {
        return ResponseEntity.ok().body(this.permissionService.handldeUpdatePermission(permission));
    }

    @ApiMessage("delete a permission")
    @DeleteMapping("/permissions/{id}")
    @Operation(summary = "Delete a permission", description = "Delete a permission by its ID.")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") Long id) {
        this.permissionService.handleDeletePermission(id);
        return ResponseEntity.ok().body(null);
    }

    @ApiMessage("fetch all permissions")
    @GetMapping("/permissions")
    @Operation(summary = "Fetch all permissions", description = "Retrieve a paginated list of all permissions with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable) {
        PaginationResponseDTO paginationResponseDTO = this.permissionService.handleGetAllPermission(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }
}
