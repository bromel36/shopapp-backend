package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier extends Base{

    private String name;
    private String address;
    private String phone;
    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "suppliers")
    @JoinTable(name = "supplies", joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<ImportOrder> importOrders;
}
