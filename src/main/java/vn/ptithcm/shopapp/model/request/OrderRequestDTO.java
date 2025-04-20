package vn.ptithcm.shopapp.model.request;


import com.fasterxml.jackson.annotation.JsonInclude;
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

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "Total money is required")
    @Min(value = 0, message = "Total money must be at least 0")
    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    private Double amountPaid;

    @NotBlank(message = "Shipping Address is required")
    private String shippingAddress;

    @NotBlank(message = "Customer Name is required")
    private String name;

    @NotBlank(message = "Customer phone number is required")
    private String phone;

    @Valid
    @NotNull(message = "Order Details is required")
    private List<OrderDetails> orderDetails;

    private OrderRequestUser user;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetails {
        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be at least 0")
        private Double price;

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
        @NotNull(message = "Id is required")
        private Long id;
    }

}
