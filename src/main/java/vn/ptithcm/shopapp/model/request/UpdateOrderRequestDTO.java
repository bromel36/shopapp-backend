package vn.ptithcm.shopapp.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.model.entity.Address;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequestDTO {

    @NotNull(message = "Order id is required")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Valid
    @NotNull(message = "Address is required")
    private UpdateOrderRequestAddress address;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderRequestAddress{
        @NotNull(message = "Address id is required")
        private Long id;
    }
}
