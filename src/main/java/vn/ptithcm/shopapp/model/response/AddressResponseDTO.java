package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.model.request.AddressRequestDTO;

import java.time.Instant;
@Setter
@Getter
public class AddressResponseDTO {
    private Long id;
    private String recipientName;

    private String phoneNumber;

    private String street;

    private String ward;

    private String district;

    private String city;

    private Boolean isDefault;

    private UserAddressResponse user;

    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

    @Getter
    @Setter
    public static class UserAddressResponse{
        private Long id;
    }
}
