package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import vn.ptithcm.shopapp.model.entity.Cart;
import vn.ptithcm.shopapp.model.request.CartRequestDTO;
import vn.ptithcm.shopapp.model.response.CartResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

import java.util.List;

public interface ICartService {
    void handleAddToCart(CartRequestDTO cartRequest);

    List<CartResponseDTO> handleFetchCartByUser();

    void handleUpdateCart(@Valid CartRequestDTO cartRequest);

    void handleRemoveProductFromCart(String productId);

    void handleRemoveAllProductsFromCart();

    void handleCheckProductQuantity(List<CartRequestDTO> cartRequests);
}
