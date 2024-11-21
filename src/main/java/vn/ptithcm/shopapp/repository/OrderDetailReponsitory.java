package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptithcm.shopapp.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailReponsitory extends JpaRepository <OrderDetail, String>, JpaSpecificationExecutor<OrderDetail> {
    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.product where od.order.id= :orderId")
    List<OrderDetail> findAllByOderAndProduct(@Param("orderId")String orderId);
}
