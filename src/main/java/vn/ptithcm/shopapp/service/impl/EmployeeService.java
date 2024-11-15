package vn.ptithcm.shopapp.service.impl;

import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.repository.EmployeeRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IEmployeeService;
import vn.ptithcm.shopapp.service.IUserService;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Employee fetchEmployeeByUserId(Long id) {
        Employee employee = employeeRepository.findByUserId(id);

        return employee;
    }

    @Override
    public Employee handleCreateEmployee(Employee employee) {
        if(employee.getUser()!= null){
            if(employee.getUser().getId() != null){
                User user = userRepository.findById(employee.getUser().getId()).orElse(null);
                if(user != null){
                    employee.setUser(user);
                    return employeeRepository.save(employee);
                }
            }
        }
        throw new IdInvalidException("User not found!!!");
    }
}
