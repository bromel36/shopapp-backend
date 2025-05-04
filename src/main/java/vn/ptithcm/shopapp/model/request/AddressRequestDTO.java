package vn.ptithcm.shopapp.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class AddressRequestDTO {

    private Long id;

    @NotBlank(message = "Recipient name must be not empty")
    private String recipientName;

    @NotBlank(message = "Phone number must be not empty")
    private String phoneNumber;

    @NotBlank(message = "Street name must be not empty")
    private String street;

    @NotBlank(message = "Ward name must be not empty")
    private String ward;

    @NotBlank(message = "District name must be not empty")
    private String district;

    @NotBlank(message = "City name must be not empty")
    private String city;

    @NotNull(message = "Default address must be not null")
    private Boolean isDefault;

    @Valid
    @NotNull(message = "User must be required")
    private UserAddressRequest user;

    @Getter
    public static class UserAddressRequest{
        @NotNull(message = "User must be required")
        private Long id;
    }
}
