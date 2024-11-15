package vn.ptithcm.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.service.ICustomerService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    @ApiMessage("create a customer")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.customerService.handleCreateCustomer(customer));
    }

    @PutMapping("/customers")
    @ApiMessage("update a customer")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok().body(this.customerService.handleUpdateCustomer(customer));
    }

    @DeleteMapping("customers/{id}")
    @ApiMessage("delete a customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        this.customerService.handleDeleteCustomer(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/customers/{id}")
    @ApiMessage("fetch customer by id")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.customerService.handleFetchCustomerById(id));
    }

    @GetMapping("/customers/by-user/{id}")
    @ApiMessage("fetch customer by id")
    public ResponseEntity<CustomerResponseDTO> getCustomerByUserId(@PathVariable("id") Long userId){
        return ResponseEntity.ok(this.customerService.handleFetchCustomerByUserId(userId));
    }
}
