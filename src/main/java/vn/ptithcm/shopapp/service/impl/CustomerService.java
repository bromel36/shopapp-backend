package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.CustomerConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.repository.CustomerRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.ICustomerService;
import vn.ptithcm.shopapp.util.PaginationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerConverter customerConverter;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository, CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerConverter = customerConverter;
    }



    @Override
    public CustomerResponseDTO handleCreateCustomer(Customer customer) {

        if (customer.getUser() == null || customer.getUser().getId() == null) {
            throw new IdInvalidException("User ID is required.");
        }

        User user = userRepository.findById(customer.getUser().getId()).orElse(null);
        if (user == null) {
            throw new IdInvalidException("User not found.");
        }

        if (user.getCustomer() != null || user.getEmployee()!= null) {
            throw new IdInvalidException("This user is already associated with a person.");
        }

        customer.setUser(user);
        customerRepository.save(customer);
        return customerConverter.convertToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO handleUpdateCustomer(Customer customer) {
        Customer customerDB = this.customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IdInvalidException("Customer not found."));

        customerDB.setFullName(customer.getFullName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setAddress(customer.getAddress());
        customerDB.setPhone(customer.getPhone());
        customerDB.setGender(customer.getGender());
        customerDB.setAvatar(customer.getAvatar());
        customerDB.setBirthday(customer.getBirthday());
        customerDB.setShoppingAddress(customerDB.getShoppingAddress());

        customerRepository.save(customerDB);

        return customerConverter.convertToCustomerResponseDTO(customerDB);
    }

    @Override
    public void handleDeleteCustomer(Long id) {

    }

    @Override
    public CustomerResponseDTO handleFetchCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Customer not found."));

        return customerConverter.convertToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO handleFetchCustomerByUserId(Long userId) {
        Customer customer = customerRepository.findByUserId(userId);
        if (customer != null) {
            return customerConverter.convertToCustomerResponseDTO(customer);
        }
        return null;
    }

    @Override
    public PaginationResponseDTO handleGetAllCustomers(Specification<Customer> spec, Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable,customerPage);

        List<CustomerResponseDTO> customerResponseDTOs = customerPage.getContent().stream()
                .map(it->customerConverter.convertToCustomerResponseDTO(it))
                .collect(Collectors.toList());

        result.setResult(customerResponseDTOs);

        return result;
    }


}
