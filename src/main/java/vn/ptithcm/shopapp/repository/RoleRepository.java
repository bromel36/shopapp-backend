package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);

    boolean existsByCode(String code);
}
