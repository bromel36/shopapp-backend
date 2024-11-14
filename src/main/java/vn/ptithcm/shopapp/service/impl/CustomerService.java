package vn.ptithcm.shopapp.service.impl;

import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.repository.CustomerRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.ICustomerService;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Customer fetchCustomerByUserId(Long id) {
        return this.customerRepository.findByUserId(id);
    }

    @Override
    public Customer handleCreateCustomer(Customer customer) {
        if(customer.getUser()!= null){
            if(customer.getUser().getId() != null){
                User user = userRepository.findById(customer.getUser().getId()).orElse(null);
                if(user != null){
                    customer.setUser(user);
                    return customerRepository.save(customer);
                }
            }
        }
        throw new IdInvalidException("User invalid!!!");
    }
}
