package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.ptithcm.shopapp.enums.GenderEnum;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String id;

    private String fullName;
    private String address;
    private String email;
    private String phone;
    private GenderEnum gender;
    private Instant birthday;
    private String shoppingAddress;
    private String avatar;
    private UserResponseDTO user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

}