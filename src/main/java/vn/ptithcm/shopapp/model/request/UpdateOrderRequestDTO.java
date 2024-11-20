package vn.ptithcm.shopapp.model.request;

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

    @NotBlank(message = "Order id is required")
    private String id;
    private OrderStatusEnum status;
    private String shippingAddress;
    private Double amountPaid;
}
