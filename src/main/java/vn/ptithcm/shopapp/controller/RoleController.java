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
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IRoleService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Role")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiMessage("create a role")
    @PostMapping("/roles")
    @Operation(summary = "Create a role", description = "Create a new role and return the created role details.")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleCreateRole(role));
    }

    @ApiMessage("update a role")
    @PutMapping("/roles")
    @Operation(summary = "Update a role", description = "Update an existing role and return the updated role details.")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) {
        return ResponseEntity.ok().body(this.roleService.handleUpdateRole(role));
    }

    @ApiMessage("delete a role")
    @DeleteMapping("/roles/{id}")
    @Operation(summary = "Delete a role", description = "Delete a role by its ID.")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id) {
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.ok().body(null);
    }

    @ApiMessage("fetch a role")
    @GetMapping("/roles/{id}")
    @Operation(summary = "Fetch a role", description = "Retrieve details of a role by its ID.")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.roleService.handleFetchRoleById(id));
    }

    @ApiMessage("fetch all roles")
    @GetMapping("/roles")
    @Operation(summary = "Fetch all roles", description = "Retrieve a paginated list of all roles with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllRoles(
            @Filter Specification<Role> spec,
            Pageable pageable) {
        PaginationResponseDTO paginationResponseDTO = this.roleService.handleGetAllRoles(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }

}
