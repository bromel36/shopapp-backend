package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.impl.PermissionService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    @ApiMessage("create a permission")
    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.handldeCreatePermission(permission));
    }

    @ApiMessage("update a permission")
    @PutMapping("/permissions")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission) {
//        return ResponseEntity.ok().body(this.permissionService.handldeUpdatePermission(permission));
    }

    @ApiMessage("delete a permission")
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") Long id) {
//        this.permissionService.handleDeletePermission(id);
        return ResponseEntity.ok().body(null);
    }


    @ApiMessage("fetch all permission")
    @GetMapping("/permissions")
    public ResponseEntity<PaginationResponseDTO> getAllResume(
            @Filter Specification<Permission> spec,
            Pageable pageable
    ){
//        PaginationResponseDTO paginationResponseDTO = this.permissionService.handleGetAllPermission(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }
}
