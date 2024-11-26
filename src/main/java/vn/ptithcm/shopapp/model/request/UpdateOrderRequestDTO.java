package vn.ptithcm.shopapp.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequestDTO {

    @NotNull(message = "Order id is required")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
    private String shippingAddress;
    private String name;
    private String phone;

    @Min(value = 1, message = "Amount paid must be at least 1")
    private Double amountPaid;
}
