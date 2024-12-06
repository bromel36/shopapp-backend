package vn.ptithcm.shopapp.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PythonApiResponse {
    private String message;
    private String tag;
    private List<String> brand;
    private List<Double> price;

}

