package vn.ptithcm.shopapp.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.StatusEnum;
import vn.ptithcm.shopapp.validation.CreateUserValidationGroup;
import vn.ptithcm.shopapp.validation.UpdateUserValidationGroup;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Base{

    @NotBlank(message = "Username must be filled", groups = CreateUserValidationGroup.class)
    private String username;

    @NotBlank(message = "Password must be filled", groups = CreateUserValidationGroup.class)
    private String password;

    @NotBlank(message = "Active status must be filled", groups = UpdateUserValidationGroup.class)
    private Boolean active;

    @Column(columnDefinition = "text")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Customer customer;


    @OneToOne(mappedBy = "user")
    private Employee employee;

}
