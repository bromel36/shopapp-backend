package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptithcm.shopapp.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
    Employee findByUserId(String id);
}
