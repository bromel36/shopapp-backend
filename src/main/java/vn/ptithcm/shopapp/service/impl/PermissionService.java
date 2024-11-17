package vn.ptithcm.shopapp.service.impl;

import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.repository.PermissionRepository;
import vn.ptithcm.shopapp.service.IPermissionService;

import java.util.List;


@Service
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> handleFetchPermissionByIds(List<String> permissionIds) {
        return this.permissionRepository.findByIdIn(permissionIds);
    }
}
