package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Cart;
import vn.ptithcm.shopapp.model.response.CartResponseDTO;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;

@Component
public class CartConverter {

    private final ModelMapper modelMapper;
    private final ProductConverter productConverter;

    public CartConverter(ModelMapper modelMapper, ProductConverter productConverter) {
        this.modelMapper = modelMapper;
        this.productConverter = productConverter;
    }

    public CartResponseDTO convertToCartResponseDTO(Cart cart) {
        ProductResponseDTO productResponse = productConverter.convertToProductResponseDTO(cart.getProduct());

        CartResponseDTO responseDTO = new CartResponseDTO();
        responseDTO.setQuantity(cart.getQuantity());
        responseDTO.setProduct(productResponse);

        return responseDTO;
    }
}
