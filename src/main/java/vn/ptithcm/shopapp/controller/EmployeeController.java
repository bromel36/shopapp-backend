package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.response.EmployeeResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IEmployeeService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    @ApiMessage("create a employee")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.employeeService.handleCreateEmployee(employee));
    }

    @PutMapping("/employees")
    @ApiMessage("update a employee")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok().body(this.employeeService.handleUpdateEmployee(employee));
    }

    @GetMapping("/employees/{id}")
    @ApiMessage("fetch a employee")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.employeeService.handleFetchEmployeeById(id));
    }

    @GetMapping("/employees/by-user/{id}")
    @ApiMessage("fetch a employee by user")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeByUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok().body(this.employeeService.handleFetchEmployeeByUserId(userId));
    }

    @GetMapping("/employees")
    @ApiMessage("fetch all employees")
    public ResponseEntity<PaginationResponseDTO> getAllEmployees(
            @Filter Specification<Employee> spec,
            Pageable pageable
    ) {
        PaginationResponseDTO paginationResponseDTO;
        paginationResponseDTO = employeeService.handleGetAllEmployees(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

}


