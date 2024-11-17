package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IRoleService {
    Role handleCreateRole(Role role);

    Role handleUpdateRole(Role role);

    void handleDeleteRole(String id);

    PaginationResponseDTO handleGetAllRoles(Specification<Role> spec, Pageable pageable);

    Role handleFetchRoleById(String id);
}
