package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.Employee;

public interface IEmployeeService {
    Employee fetchEmployeeByUserId(Long id);

    Employee handleCreateEmployee(Employee employee);
}
