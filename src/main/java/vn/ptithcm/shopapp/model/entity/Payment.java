package vn.ptithcm.shopapp.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends Base{
    private Instant date;
    private Double amountPaid;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
