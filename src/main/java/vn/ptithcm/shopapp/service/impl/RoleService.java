package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.RoleRepository;
import vn.ptithcm.shopapp.service.IPermissionService;
import vn.ptithcm.shopapp.service.IRoleService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final IPermissionService permissionService;
    public RoleService(RoleRepository roleRepository, IPermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public Role handleCreateRole(Role role) {
        if(isExistsCode(role.getCode())){
            throw new IdInvalidException("Role already exists");
        }

        if (role.getPermissions()!= null && !role.getPermissions().isEmpty()) {
            List<Permission> permissions = role.getPermissions();

            List<Long> permissionIds = permissions.stream().map(Permission::getId).collect(Collectors.toList());

            List<Permission> permissionDB = this.permissionService.handleFetchPermissionByIds(permissionIds);

            role.setPermissions(permissionDB);
        }

        return roleRepository.save(role);
    }

    @Override
    public Role handleUpdateRole(Role role) {
        return null;
    }

    @Override
    public void handleDeleteRole(Long id) {

    }

    @Override
    public PaginationResponseDTO handleGetAllPermission(Specification<Role> spec, Pageable pageable) {
        return null;
    }

    public boolean isExistsCode(String code) {
        return this.roleRepository.existsByCode(code);
    }

    public Role handleFetchRoleById(Long id){
        Role role = this.roleRepository.findById(id)
                .orElseThrow(()->  new IdInvalidException("Role not found with id = "+ id));
        return role;
    }
}
