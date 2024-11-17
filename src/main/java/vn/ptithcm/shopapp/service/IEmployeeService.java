package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.response.EmployeeResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IEmployeeService {

    EmployeeResponseDTO handleCreateEmployee(Employee employee);

    EmployeeResponseDTO handleUpdateEmployee(Employee employee);

    EmployeeResponseDTO handleFetchEmployeeById(String id);

    EmployeeResponseDTO handleFetchEmployeeByUserId(String userId);

    PaginationResponseDTO handleGetAllEmployees(Specification<Employee> spec, Pageable pageable);
}
