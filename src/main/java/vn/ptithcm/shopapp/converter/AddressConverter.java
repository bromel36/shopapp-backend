package vn.ptithcm.shopapp.converter;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Address;
import vn.ptithcm.shopapp.model.request.AddressRequestDTO;
import vn.ptithcm.shopapp.model.response.AddressResponseDTO;

@Slf4j
@Component
public class AddressConverter {

    private final ModelMapper modelMapper;

    public AddressConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Address convertToAddress(AddressRequestDTO dto) {
        return modelMapper.map(dto, Address.class);
    }

    public AddressResponseDTO convertToAddressReponse(Address address) {
//        log.info("created at: {}", address.getCreatedAt());
        return modelMapper.map(address, AddressResponseDTO.class);
    }
}