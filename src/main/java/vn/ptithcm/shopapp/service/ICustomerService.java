package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface ICustomerService {

    CustomerResponseDTO handleCreateCustomer(Customer customer);

    CustomerResponseDTO handleUpdateCustomer(Customer customer);

    void handleDeleteCustomer(String id);

    CustomerResponseDTO handleFetchCustomerById(String id);

    CustomerResponseDTO handleFetchCustomerByUserId(String userId);

    PaginationResponseDTO handleGetAllCustomers(Specification<Customer> spec, Pageable pageable);
}
