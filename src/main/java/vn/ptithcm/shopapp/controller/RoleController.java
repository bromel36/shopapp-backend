package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
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
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiMessage("create a role")
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.handleCreateRole(role));
    }


    @ApiMessage("update a role")
    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) {
        return ResponseEntity.ok().body(this.roleService.handleUpdateRole(role));
    }

    @ApiMessage("delete a role")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") String id) {
        this.roleService.handleDeleteRole(id);
        return ResponseEntity.ok().body(null);
        // chưa viết gì trong service
    }


    @ApiMessage("fetch a role")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.roleService.handleFetchRoleById(id));
    }

    @ApiMessage("fetch all roles")
    @GetMapping("/roles")
    public ResponseEntity<PaginationResponseDTO> getAllRoles(
            @Filter Specification<Role> spec,
            Pageable pageable
    ){
        PaginationResponseDTO paginationResponseDTO = this.roleService.handleGetAllRoles(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }

}
