package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptithcm.shopapp.model.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}