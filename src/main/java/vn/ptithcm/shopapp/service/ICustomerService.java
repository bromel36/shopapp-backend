package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.Customer;

public interface ICustomerService {
    Customer fetchCustomerByUserId(Long id);

    Customer handleCreateCustomer(Customer customer);
}
