package vn.ptithcm.shopapp.service;


import vn.ptithcm.shopapp.model.entity.Permission;

import java.util.List;

public interface IPermissionService {
    List<Permission> handleFetchPermissionByIds(List<Long> permissionIds);
}
