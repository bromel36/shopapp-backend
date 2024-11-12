package vn.ptithcm.shopapp.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.StatusEnum;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends Base{

    private String username;

    private String password;

    private Boolean active;

    private String avatar;

    @Column(columnDefinition = "text")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
