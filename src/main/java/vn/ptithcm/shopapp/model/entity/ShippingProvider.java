package vn.ptithcm.shopapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shipping_providers")
@Getter
@Setter
public class ShippingProvider extends Base{

    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "shippingProvider", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Shipping> shippingList;
}
