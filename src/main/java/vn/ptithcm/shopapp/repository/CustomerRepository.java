package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptithcm.shopapp.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
    Customer findByUserId(String id);
}
