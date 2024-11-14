package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.GenderEnum;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer extends Base {
    private String fullName;
    private String address;
    private String email;

    private String phone;
    private GenderEnum gender;

    private Instant birthday;

    private String shoppingAddress;

    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Order> orders;
}
