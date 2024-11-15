package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;

public interface ICustomerService {

    CustomerResponseDTO handleCreateCustomer(Customer customer);

    CustomerResponseDTO handleUpdateCustomer(Customer customer);

    void handleDeleteCustomer(Long id);

    CustomerResponseDTO handleFetchCustomerById(Long id);

    CustomerResponseDTO handleFetchCustomerByUserId(Long userId);
}
