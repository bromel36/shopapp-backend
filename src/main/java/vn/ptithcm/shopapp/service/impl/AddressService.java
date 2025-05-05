package vn.ptithcm.shopapp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.AddressConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Address;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.AddressRequestDTO;
import vn.ptithcm.shopapp.model.response.AddressResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.AddressRepository;
import vn.ptithcm.shopapp.service.IAddressService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j(topic = "ADDRESS-SERVICE")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddressService implements IAddressService {

    AddressConverter addressConverter;
    IUserService userService;
    AddressRepository addressRepository;

    @Override
    public AddressResponseDTO handleCreateAddress(AddressRequestDTO address) {
        User userDB = validatePermission(address.getUser().getId());

        if(address.getIsDefault()){
            List<Address> addresses = userDB.getAddresses();
            addresses.forEach(it-> it.setIsDefault(false));
        }

        Address addressEntity = addressConverter.convertToAddress(address);


        addressEntity.setUser(userDB);

        addressRepository.save(addressEntity);

        return addressConverter.convertToAddressReponse(addressEntity);
    }

    @Override
    public AddressResponseDTO handleUpdateAddress(AddressRequestDTO address) {
        User userDB = validatePermission(address.getUser().getId());

        Address addressDB =  handleFetchAddressById(address.getId());

        addressDB.setUser(userDB);

        addressDB.setRecipientName(address.getRecipientName());
        addressDB.setPhoneNumber(address.getPhoneNumber());
        addressDB.setStreet(address.getStreet());
        addressDB.setWard(address.getWard());
        addressDB.setDistrict(address.getDistrict());
        addressDB.setCity(address.getCity());

        // dang false thanh true
        if (address.getIsDefault() && addressDB.getIsDefault()!= address.getIsDefault()) {
            List<Address> addresses = userDB.getAddresses();
            addresses.forEach(it -> it.setIsDefault(false));
        }
        addressDB.setIsDefault(address.getIsDefault());

        addressRepository.save(addressDB);

        return addressConverter.convertToAddressReponse(addressDB);
    }

    @Override
    public AddressResponseDTO handleFetchAddressResponseById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Address not found"));
        return addressConverter.convertToAddressReponse(address);
    }

    @Override
    public PaginationResponseDTO handldeFetchAllAddress(Specification<Address> spec, Pageable pageable) {
        Page<Address> addresses = addressRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, addresses);

        List<AddressResponseDTO> addressResponses = addresses.getContent().stream()
                .map(addressConverter::convertToAddressReponse).toList();

        result.setResult(addressResponses);

        return result;
    }

    @Override
    public void handleDeleteAddress(Long id) {
        Address addressDB = handleFetchAddressById(id);
        addressRepository.delete(addressDB);
    }

    public User validatePermission(Long userId){

        User userDB = userService.getUserById(userId);

        User currentUserLogin = userService.getUserLogin();

        if (SecurityUtil.isCustomer(currentUserLogin) && currentUserLogin.getId() != userDB.getId()) {
            throw new AccessDeniedException("Customers are only insert/update address for themselves");
        }

        return userDB;
    }
    public Address handleFetchAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Address not found"));
        return address;
    }
}
