package vn.ptithcm.shopapp.model.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponseDTO {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

    private List<ProductSupplier> products;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductSupplier{
        private String id;
        private String name;
        private String thumbnail;
        private String brand;
    }
}
