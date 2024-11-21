package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.request.ProductRequestDTO;
import vn.ptithcm.shopapp.model.response.ProductResponseDTO;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.List;

@Component
public class ProductConverter {

    private final ModelMapper modelMapper;

    public ProductConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public void convertToProduct(ProductRequestDTO dto, Product product){

         modelMapper.map(dto, product);

        if(dto.getSlider()!= null && !dto.getSlider().isEmpty()){
            String sliders = String.join(" ", dto.getSlider());
            product.setSlider(sliders);
        }
    }

    public ProductResponseDTO convertToProductResponseDTO(Product product){
        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);

        if(StringUtil.isValid(product.getSlider())){
            List<String> sliders = List.of(product.getSlider().split(" "));
            productResponseDTO.setSlider(sliders);
        }

        return productResponseDTO;
    }
}
