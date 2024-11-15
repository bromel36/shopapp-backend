package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.validation.CreateUserValidationGroup;
import vn.ptithcm.shopapp.validation.UpdateUserValidationGroup;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Base{

    @NotBlank(message = "Username must be filled", groups = CreateUserValidationGroup.class)
    private String username;

    @NotBlank(message = "Password must be filled", groups = CreateUserValidationGroup.class)
    private String password;

    @NotNull(message = "Active must not be null", groups = UpdateUserValidationGroup.class)
    private Boolean active;

    @Column(columnDefinition = "text")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Employee employee;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Cart> carts;

}
