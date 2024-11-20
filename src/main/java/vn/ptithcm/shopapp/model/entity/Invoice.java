package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice extends Base{

    Instant date;
    String tax;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
