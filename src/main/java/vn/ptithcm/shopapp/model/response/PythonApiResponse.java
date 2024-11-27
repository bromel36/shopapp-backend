package vn.ptithcm.shopapp.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PythonApiResponse {
    private String message;
    private String tag;
    private String brand;
    private String price;

}

