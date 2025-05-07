package vn.ptithcm.shopapp.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.TokenTypeEnum;

@Entity
@Table(name = "tokens")
@Getter
@Setter
public class Token extends Base{

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
