package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.GenderEnum;

import java.time.Instant;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private Boolean active;

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
    }
}
