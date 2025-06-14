package vn.ptithcm.shopapp.model.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
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
    @NotEmpty(message = "Must have at least one item", groups = {AdminCreateOrderValidationGroup.class, CustomerCreateOrderValidationGroup.class})
    private List<OrderDetails> orderDetails;

    @Valid
    @NotNull(message = "User is required", groups = AdminCreateOrderValidationGroup.class)
    private OrderRequestUser user;

    @NotBlank(message = "Recipient name is required")
    private String name;
    @NotBlank(message = "Recipient address is required")
    private String shippingAddress;
    @NotBlank(message = "Recipient phone is required")
    private String phone;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetails {
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1",groups = {AdminCreateOrderValidationGroup.class, CustomerCreateOrderValidationGroup.class})
        private Integer quantity;

        @NotNull(message = "Product is required",groups = {AdminCreateOrderValidationGroup.class, CustomerCreateOrderValidationGroup.class})
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

}
