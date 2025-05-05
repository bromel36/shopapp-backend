package vn.ptithcm.shopapp.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequestDTO {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Order total money is required")
    private Double amount;
}
