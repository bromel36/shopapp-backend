package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
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
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.handleFetchOrderResponse(id));
    }

    @GetMapping("/user-orders/{userId}")
    @ApiMessage("get user order")
    public ResponseEntity<PaginationResponseDTO> getOrderByUserId(@PathVariable("userId") Long id,
                                                                  Pageable pageable) {
        return ResponseEntity.ok().body(orderService.handleFetchOrderByUserId(id, pageable));
    }


    @GetMapping("/orders")
    @ApiMessage("fetch all orders")
    public ResponseEntity<PaginationResponseDTO> getAllOrders(
            @Filter Specification<Order> spec,
            Pageable pageable
    ){
        PaginationResponseDTO paginationResponseDTO = orderService.handlFetchAllOrders(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }
}
