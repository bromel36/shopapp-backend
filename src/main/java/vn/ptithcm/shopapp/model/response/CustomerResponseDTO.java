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
    private Long id;

    private String fullName;
    private String address;
    private String email;
    private String phone;
    private GenderEnum gender;
    private Instant birthday;
    private String shoppingAddress;
    private String avatar;
    private CustomerUser user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerUser {
        private Long id;
        private String username;
        private Boolean active;
    }
}