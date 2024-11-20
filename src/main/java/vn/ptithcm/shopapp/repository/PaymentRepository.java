package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}