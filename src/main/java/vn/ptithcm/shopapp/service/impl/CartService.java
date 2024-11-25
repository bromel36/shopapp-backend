package vn.ptithcm.shopapp.service.impl;

import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.converter.CartConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.error.OutOfStockException;
import vn.ptithcm.shopapp.model.entity.Cart;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.CartRequestDTO;
import vn.ptithcm.shopapp.model.response.CartResponseDTO;
import vn.ptithcm.shopapp.model.response.ProductQuantityResponse;
import vn.ptithcm.shopapp.repository.CartRepository;
import vn.ptithcm.shopapp.service.ICartService;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    private final FilterParser filterParser;

    private final FilterSpecificationConverter filterSpecificationConverter;

    private final CartRepository cartRepository;
    private final IProductService productService;
    private final IUserService userService;
    private final CartConverter cartConverter;

    public CartService(FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter, CartRepository cartRepository, IProductService productService, IUserService userService, CartConverter cartConverter) {
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.cartConverter = cartConverter;
    }


    @Override
    public void handleAddToCart(CartRequestDTO cartRequest) {

        User userDB = userService.getUserLogin();


        Product productDB = productService.handleFetchProductById(cartRequest.getProduct().getId());

        Cart cartDB = fetchCartByUserAndProduct(productDB, userDB);

        if (cartDB != null) {
            Integer quantity = cartDB.getQuantity() + cartRequest.getQuantity();

            validateQuantity(productDB, quantity);

            cartDB.setQuantity(quantity);

            cartRepository.save(cartDB);
        } else {

            validateQuantity(productDB, cartRequest.getQuantity());

            Cart toSave = new Cart();
            toSave.setProduct(productDB);
            toSave.setQuantity(cartRequest.getQuantity());
            toSave.setUser(userDB);


            cartRepository.save(toSave);
        }
    }

    @Override
    public List<CartResponseDTO> handleFetchCartByUser() {
        User userDB = userService.getUserLogin();

        List<Cart> carts = cartRepository.findAllByUser(userDB);

        return carts
                .stream()
                .map(it -> cartConverter.convertToCartResponseDTO(it)).toList();
    }

    @Override
    public void handleUpdateCart(CartRequestDTO cartRequest) {
        User userDB = userService.getUserLogin();
        Product productDB = productService.handleFetchProductById(cartRequest.getProduct().getId());

        Cart cartDB = fetchCartByUserAndProduct(productDB, userDB);

        if (cartDB != null) {
            validateQuantity(productDB, cartRequest.getQuantity());

            cartDB.setQuantity(cartRequest.getQuantity());

            cartRepository.save(cartDB);
        } else {
            throw new IdInvalidException("Product not found");
        }

    }

    @Override
    @Transactional
    public void handleRemoveProductFromCart(String productId) {
        User userDB = userService.getUserLogin();

        Product product = productService.handleFetchProductById(productId);

        cartRepository.deleteByProductAndUser(product, userDB);
    }

    @Override
    @Transactional
    public void handleRemoveAllProductsFromCart() {
        User userDB = userService.getUserLogin();

        cartRepository.deleteAllByUser(userDB);
    }

    @Override
    public void handleCheckProductQuantity(List<CartRequestDTO> cartRequests) {
        List<String> productIds = cartRequests.stream().map(it -> it.getProduct().getId()).toList();

        Map<String, Integer> requestMap = convertToMap(cartRequests);

        List<Product> productDBs = productService.handleFetchAllProductByIds(productIds);

        List<ProductQuantityResponse> outOfStockList = new ArrayList<>();

        productDBs.forEach(product -> {
            ProductQuantityResponse pqr = new ProductQuantityResponse();
            Integer quantity = requestMap.get(product.getId());

            if (product.getQuantity() < quantity) {
                pqr.setId(product.getId());
                pqr.setQuantity(product.getQuantity());
                outOfStockList.add(pqr);
            }

        });
        if (!outOfStockList.isEmpty()) {
            throw new OutOfStockException("There are products that do not meet the required quantity", outOfStockList);
        }
    }

    public Cart fetchCartByUserAndProduct(Product product, User user) {
        return this.cartRepository.findCartByProductAndUser(product, user);
    }

    private void validateQuantity(Product product, Integer requestedQuantity) {
        if (product.getQuantity() < requestedQuantity) {
            throw new IdInvalidException("Cart quantity exceeds product quantity");
        }
    }

    public Map<String, Integer> convertToMap(List<CartRequestDTO> cartRequestDTOs) {
        if (cartRequestDTOs == null || cartRequestDTOs.isEmpty()) {
            throw new IllegalArgumentException("List of CartRequestDTO cannot be null or empty");
        }

        return cartRequestDTOs.stream()
                .collect(Collectors.toMap(
                        cartRequest -> cartRequest.getProduct().getId(),
                        cartRequest -> cartRequest.getQuantity()
                ));
    }


}
