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
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;

import java.time.Instant;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

    OrderResponseDTO.CustomerOrder customer;
    OrderResponseDTO.EmployeeOrder employee;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerOrder{
        private String id;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeOrder{
        private String id;
    }
}
