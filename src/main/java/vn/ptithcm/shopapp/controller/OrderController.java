package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.CreateOrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;
import vn.ptithcm.shopapp.validation.AdminCreateOrderValidationGroup;
import vn.ptithcm.shopapp.validation.CustomerCreateOrderValidationGroup;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Order")
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public OrderController(IOrderService orderService, IUserService userService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.orderService = orderService;
        this.userService = userService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @PostMapping("/orders")
    @ApiMessage("Customer place order success")
    @Operation(summary = "Customer place an order", description = "Create a new order and return the order details.")
    public ResponseEntity<OrderResponseDTO> createOrder(@Validated(CustomerCreateOrderValidationGroup.class) @RequestBody CreateOrderRequestDTO orderRequest) {
        User currentUserLogin = userService.getUserLogin();

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.handleCreateOrder(orderRequest, currentUserLogin));
    }

    @PostMapping("/admin-orders")
    @ApiMessage("Admin created order successfully")
    @Operation(summary = "Admin create an order", description = "Create a new order and return the order details.")
    public ResponseEntity<OrderResponseDTO> createAdminOrder(@Validated(AdminCreateOrderValidationGroup.class) @RequestBody CreateOrderRequestDTO orderRequest) {

        if (orderRequest.getUser() == null || orderRequest.getUser().getId() == null) {
            throw new IdInvalidException("User must be required");
        }

        User userOrder = userService.getUserById(orderRequest.getUser().getId());
        if (!SecurityUtil.isCustomer(userOrder)) {
            throw new IdInvalidException("User must be a customer");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.handleCreateOrder(orderRequest, userOrder));
    }

    @PutMapping("/orders")
    @ApiMessage("Customer update order")
    @Operation(summary = "Customer update an order", description = "Update an existing order and return the updated order details.")
    public ResponseEntity<OrderResponseDTO> updateOrder(@Valid @RequestBody UpdateOrderRequestDTO ordRequest) {
        User currentUserLogin = userService.getUserLogin();
        return ResponseEntity.ok().body(orderService.handleCustomerUpdateOrder(ordRequest, currentUserLogin));
    }


    @PutMapping("/admin-orders")
    @ApiMessage("Admin update order")
    @Operation(summary = "Admin update an order", description = "Update an existing order and return the updated order details.")
    public ResponseEntity<OrderResponseDTO> updateAdminOrder(@Valid @RequestBody UpdateOrderRequestDTO ordRequest) {
        return ResponseEntity.ok().body(orderService.handleAdminUpdateOrder(ordRequest));
    }

    @GetMapping("/orders/{id}")
    @ApiMessage("get order")
    @Operation(summary = "Fetch an order", description = "Retrieve details of an order by its ID.")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.handleFetchOrderResponse(id));
    }

    @GetMapping("/user-orders/{userId}")
    @ApiMessage("get user order")
    @Operation(summary = "Fetch user orders", description = "Retrieve a paginated list of orders for a specific user.")
    public ResponseEntity<PaginationResponseDTO> getOrderByUserId(@PathVariable("userId") Long id,
                                                                  Pageable pageable) {
        return ResponseEntity.ok().body(orderService.handleFetchOrderByUserId(id, pageable));
    }

    @GetMapping("/orders")
    @ApiMessage("fetch all orders")
    @Operation(
            summary = "Fetch all orders",
            description = "Retrieve a paginated list of all orders with optional filtering."
    )
    public ResponseEntity<PaginationResponseDTO> getAllOrders(
            @Parameter(
                    description = "Filtering expression (e.g., id:'1')",
                    example = "id:'1'"
            )
            @RequestParam(name = "filter", required = false) String filter,

            @ParameterObject Pageable pageable
    ) {
        Specification<Order> spec = filter == null ? null : filterSpecificationConverter.convert(filterParser.parse(filter));
        PaginationResponseDTO paginationResponseDTO = orderService.handlFetchAllOrders(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

}
