package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.GenderEnum;
import vn.ptithcm.shopapp.validation.CreateUserValidationGroup;
import vn.ptithcm.shopapp.validation.UpdateUserValidationGroup;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Base{

    @NotBlank(message = "Username must be filled", groups = CreateUserValidationGroup.class)
    private String email;

    @NotBlank(message = "Password must be filled", groups = CreateUserValidationGroup.class)
    private String password;

    @NotNull(message = "Active must not be null", groups = UpdateUserValidationGroup.class)
    private Boolean active;

    @Column(columnDefinition = "text")
    private String refreshToken;

    private String fullName;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Instant birthday;

    private String shoppingAddress;

    private String avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokens;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Cart> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ImportOrder> importOrders;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InventoryLog> inventoryLogs;

}
