package vn.ptithcm.shopapp.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/orders")
    @ApiMessage("customer place order")
    public ResponseEntity<OrderResponseDTO> createCustomerOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.handleCreateOrder(orderRequest));
    }

}
