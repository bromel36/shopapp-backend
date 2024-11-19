package vn.ptithcm.shopapp.model.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.enums.PaymentMethodEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {


    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerOrder customer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EmployeeOrder employee;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerOrder {
        private String id;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeOrder {
        private String id;
    }


}
