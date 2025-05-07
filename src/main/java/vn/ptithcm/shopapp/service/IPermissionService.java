package vn.ptithcm.shopapp.service;


import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

import java.util.List;

public interface IPermissionService {
    List<Permission> handleFetchPermissionByIds(List<Long> permissionIds);

    Permission handldeCreatePermission(@Valid Permission permission);

    Permission handldeUpdatePermission(@Valid Permission permission);

    void handleDeletePermission(Long id);

    PaginationResponseDTO handleGetAllPermission(Specification<Permission> spec, Pageable pageable);

    boolean isExistByApiPathAndMethod(String apiPath, String method);
}
