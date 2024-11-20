package vn.ptithcm.shopapp.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
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
    @ApiMessage("place order")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.handleCreateOrder(orderRequest));
    }


    @PutMapping("/orders")
    @ApiMessage("update order")
    public ResponseEntity<OrderResponseDTO> updateOrder(@Valid @RequestBody UpdateOrderRequestDTO ordRequest) {

        return ResponseEntity.ok().body(orderService.handleUpdateOrder(ordRequest));
    }

    @GetMapping("/orders/{id}")
    @ApiMessage("get order")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(orderService.handleFetchOrderResponse(id));

    }


}
