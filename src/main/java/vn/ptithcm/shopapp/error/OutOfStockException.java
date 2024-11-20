package vn.ptithcm.shopapp.error;

import vn.ptithcm.shopapp.model.response.ProductQuantityResponse;

import java.util.List;

public class OutOfStockException extends RuntimeException {
    private final List<ProductQuantityResponse> outOfStockItems;

    public OutOfStockException(String message, List<ProductQuantityResponse> outOfStockItems) {
        super(message);
        this.outOfStockItems = outOfStockItems;
    }

    public List<ProductQuantityResponse> getOutOfStockItems() {
        return outOfStockItems;
    }
}
