package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.enums.PaymentMethodEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    private String shippingAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;

    private UserOrder user;

    private List<OrderDetailsResponse> orderDetails;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserOrder{
        private String id;
        private String email;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailsResponse{
        private String id;
        private Double price;
        private Integer quantity;
        private String productName;
        private String productThumbnail;
    }

}
