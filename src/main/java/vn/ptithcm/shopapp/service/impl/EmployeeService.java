package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.EmployeeConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;
import vn.ptithcm.shopapp.model.response.EmployeeResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.EmployeeRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IEmployeeService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeConverter employeeConverter;
    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, EmployeeConverter employeeConverter) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public EmployeeResponseDTO handleCreateEmployee(Employee employee) {
        if(employee.getUser()!= null && employee.getUser().getId() != null) {
            throw new IdInvalidException("User ID is required!!!");
        }

        User user = userRepository.findById(employee.getUser().getId()).orElse(null);
        if (user == null) {
            throw new IdInvalidException("User not found.");
        }

        if (user.getEmployee() != null || user.getCustomer()!= null) {
            throw new IdInvalidException("This user is already associated with a person.");
        }

        employee.setUser(user);
        employeeRepository.save(employee);

        return employeeConverter.convertToEmployeeResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO handleUpdateEmployee(Employee employee) {
        Employee employeeDB = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new IdInvalidException("Employee not found."));

        employeeDB.setFullName(employee.getFullName());
        employeeDB.setEmail(employee.getEmail());
        employeeDB.setAddress(employee.getAddress());
        employeeDB.setPhone(employee.getPhone());
        employeeDB.setGender(employee.getGender());
        employeeDB.setAvatar(employee.getAvatar());
        employeeDB.setBirthday(employee.getBirthday());
        employeeDB.setHireDate(employee.getHireDate());
        employeeDB.setSalary(employee.getSalary());

        employeeRepository.save(employeeDB);

        return employeeConverter.convertToEmployeeResponseDTO(employeeDB);
    }

    @Override
    public EmployeeResponseDTO handleFetchEmployeeById(Long id) {
        Employee employeeDB = employeeRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Employee not found."));

        return employeeConverter.convertToEmployeeResponseDTO(employeeDB);
    }

    @Override
    public EmployeeResponseDTO handleFetchEmployeeByUserId(Long userId) {
        Employee employee = employeeRepository.findByUserId(userId);
        if (employee != null) {
            return employeeConverter.convertToEmployeeResponseDTO(employee);
        }
        return null;
    }

    @Override
    public PaginationResponseDTO handleGetAllEmployees(Specification<Employee> spec, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable,employeePage);

        List<EmployeeResponseDTO> employeeResponseDTOs = employeePage.getContent().stream()
                .map(it->employeeConverter.convertToEmployeeResponseDTO(it))
                .collect(Collectors.toList());

        result.setResult(employeeResponseDTOs);

        return result;
    }
}
