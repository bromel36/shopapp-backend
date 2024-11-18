package vn.ptithcm.shopapp.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityResponse {
    private String id;
    private int quantity;
}
