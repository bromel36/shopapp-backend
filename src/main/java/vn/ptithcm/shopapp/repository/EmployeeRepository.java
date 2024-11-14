package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUserId(Long id);
}
