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
import vn.ptithcm.shopapp.enums.PaymentMethodEnum;
import vn.ptithcm.shopapp.validation.AdminCreateOrderValidationGroup;
import vn.ptithcm.shopapp.validation.CustomerCreateOrderValidationGroup;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Valid
    @NotNull(message = "Order Details is required", groups = {AdminCreateOrderValidationGroup.class, CustomerCreateOrderValidationGroup.class})
    private List<OrderDetails> orderDetails;

    @Valid
    @NotNull(message = "User is required", groups = AdminCreateOrderValidationGroup.class)
    private OrderRequestUser user;

    @Valid
    @NotNull(message = "Address is required", groups = CustomerCreateOrderValidationGroup.class)
    private OrderRequestAddress address;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetails {
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        @NotNull(message = "Product is required")
        private String productId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequestUser{
        @NotNull(message = "User id is required", groups = AdminCreateOrderValidationGroup.class)
        private Long id;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequestAddress{
        @NotNull(message = "Address id is required", groups = CustomerCreateOrderValidationGroup.class)
        private Long id;
    }
}
