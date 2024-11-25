package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptithcm.shopapp.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COALESCE(SUM(p.amountPaid), 0) FROM Payment p WHERE p.order.id = :orderId")
    double sumPaymentsByOrderId(@Param("orderId") String orderId);
}
