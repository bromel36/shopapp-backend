package vn.ptithcm.shopapp.model.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDTO {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Product is required")
    private ProductRequestDTO product;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRequestDTO {
        @NotBlank(message = "Product ID is required")
        private String id;
    }
}

