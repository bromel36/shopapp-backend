package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import vn.ptithcm.shopapp.enums.GenderEnum;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private Boolean active;
    private String fullName;
    private String address;
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Instant birthday;

    private String avatar;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RoleResponse role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleResponse{
        private Long id;
        private String code;
        private String name;
    }
}
