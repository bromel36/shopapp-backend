package vn.ptithcm.shopapp.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.entity.Supplier;
import vn.ptithcm.shopapp.model.response.SupplierResponseDTO;

import java.util.List;

@Component
public class SupplierConverter {

    private final ModelMapper modelMapper;

    public SupplierConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SupplierResponseDTO convertToSupplierResponseDTO(Supplier supplier) {
        SupplierResponseDTO result = modelMapper.map(supplier, SupplierResponseDTO.class);

        List<Product> products = supplier.getProducts();

        List<SupplierResponseDTO.ProductSupplier> productSuppliers = products
                .stream()
                .map(it-> new SupplierResponseDTO.ProductSupplier(it.getId(),it.getName(),it.getThumbnail(),it.getBrand().getName()))
                .toList();

        result.setProducts(productSuppliers);

        return result;
    }
}
