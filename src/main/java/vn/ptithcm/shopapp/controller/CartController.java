package vn.ptithcm.shopapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.request.CartRequestDTO;
import vn.ptithcm.shopapp.model.response.CartResponseDTO;
import vn.ptithcm.shopapp.service.ICartService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    @ApiMessage("add to cart")
    @Operation(summary = "Add to cart", description = "Add a product to the user's cart.")
    public ResponseEntity<Void> addCart(@Valid @RequestBody CartRequestDTO cartRequest) {
        this.cartService.handleAddToCart(cartRequest);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/carts")
    @ApiMessage("update cart")
    @Operation(summary = "Update cart", description = "Update the quantity or details of a product in the user's cart.")
    public ResponseEntity<Void> updateCart(@Valid @RequestBody CartRequestDTO cartRequest) {
        this.cartService.handleUpdateCart(cartRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/carts")
    @ApiMessage("get all products of user")
    @Operation(summary = "Get all products in cart", description = "Retrieve all products in the user's cart.")
    public ResponseEntity<List<CartResponseDTO>> getCartByUser() {

        return ResponseEntity.ok().body(cartService.handleFetchCartByUser());
    }

    @DeleteMapping("/carts/{productId}")
    @ApiMessage("remove a product from cart")
    @Operation(summary = "Remove a product from cart", description = "Remove a specific product from the user's cart by product ID.")
    public ResponseEntity<Void> removeProduct(
            @PathVariable(value = "productId", required = true) String productId) {

        cartService.handleRemoveProductFromCart(productId);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/carts/clear")
    @ApiMessage("clear all cart")
    @Operation(summary = "Clear all cart", description = "Remove all products from the user's cart.")
    public ResponseEntity<Void> removeAllProducts() {

        cartService.handleRemoveAllProductsFromCart();

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/carts/check")
    @ApiMessage("check before order")
    @Operation(summary = "Check product quantity", description = "Check the availability of product quantities before placing an order.")
    public ResponseEntity<Void> checkQuantity(@Valid @RequestBody List<CartRequestDTO> cartRequests) {

        cartService.handleCheckProductQuantity(cartRequests);

        return ResponseEntity.ok().body(null);
    }

}
