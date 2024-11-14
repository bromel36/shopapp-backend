package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserId(Long id);
}
