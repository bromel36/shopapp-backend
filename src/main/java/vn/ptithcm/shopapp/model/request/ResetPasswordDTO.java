package vn.ptithcm.shopapp.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "Password is required")
    private String newPassword;

}

