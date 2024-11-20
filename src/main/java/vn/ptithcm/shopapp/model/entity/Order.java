package vn.ptithcm.shopapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.enums.PaymentMethodEnum;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends Base {

    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductInstance> productInstances;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> payments;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InventoryLog> inventoryLogs;
}
