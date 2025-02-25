package vn.ptithcm.shopapp.controller;

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
public class CartController {


    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/carts")
    @ApiMessage("add to cart")
    public ResponseEntity<Void> addCart(@Valid @RequestBody CartRequestDTO cartRequest) {
        this.cartService.handleAddToCart(cartRequest);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/carts")
    @ApiMessage("update cart")
    public ResponseEntity<Void> updateCart(@Valid @RequestBody CartRequestDTO cartRequest) {
        this.cartService.handleUpdateCart(cartRequest);
        return ResponseEntity.ok(null);
    }


    @GetMapping("/carts")
    @ApiMessage("get all products of user")
    public ResponseEntity<List<CartResponseDTO>> getCartByUser() {

        return ResponseEntity.ok().body(cartService.handleFetchCartByUser());
    }


    @DeleteMapping("/carts/{productId}")
    @ApiMessage("remove a product from cart")
    public ResponseEntity<Void> removeProduct(
            @PathVariable(value = "productId", required = true) String productId
    ) {

        cartService.handleRemoveProductFromCart(productId);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/carts/clear")
    @ApiMessage("clear all cart")
    public ResponseEntity<Void> removeAllProducts() {

        cartService.handleRemoveAllProductsFromCart();

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/carts/check")
    @ApiMessage("check before order")
    public ResponseEntity<Void> checkQuantity(@Valid @RequestBody List<CartRequestDTO> cartRequests) {

        cartService.handleCheckProductQuantity(cartRequests);

        return ResponseEntity.ok().body(null);
    }

}
