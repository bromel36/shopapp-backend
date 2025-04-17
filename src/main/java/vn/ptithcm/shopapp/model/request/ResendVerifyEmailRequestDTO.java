package vn.ptithcm.shopapp.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.TokenTypeEnum;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResendVerifyEmailRequestDTO {

    @Email(message = "This field require  email format")
    private String email;

    @NotNull(message = "Token type must be not null")
    private TokenTypeEnum type;
}
