package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Cart;
import vn.ptithcm.shopapp.model.response.CartResponseDTO;

@Component
public class CartConverter {

    private final ModelMapper modelMapper;

    public CartConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartResponseDTO convertToCartResponseDTO(Cart cart) {
        CartResponseDTO.ProductResponse productResponse = modelMapper.map(cart.getProduct(), CartResponseDTO.ProductResponse.class);

        CartResponseDTO responseDTO = new CartResponseDTO();
        responseDTO.setQuantity(cart.getQuantity());
        responseDTO.setProduct(productResponse);

        return responseDTO;
    }
}
