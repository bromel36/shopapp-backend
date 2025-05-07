package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptithcm.shopapp.model.entity.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    List<Permission> findByIdIn(List<Long> permissionIds);
    boolean existsPermissionByApiPathAndAndModuleAndMethod(String apiPath, String module, String method);
    boolean existsPermissionByApiPathAndMethod(String apiPath, String method);
}
