package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
