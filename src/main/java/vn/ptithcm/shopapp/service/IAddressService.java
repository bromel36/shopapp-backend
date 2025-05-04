package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Address;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.request.AddressRequestDTO;
import vn.ptithcm.shopapp.model.response.AddressResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IAddressService {
    AddressResponseDTO handleCreateAddress(AddressRequestDTO address);

    AddressResponseDTO handleUpdateAddress(AddressRequestDTO address);

    AddressResponseDTO handleFetchAddressResponseById(Long id);

    Address handleFetchAddressById(Long id);

    PaginationResponseDTO handldeFetchAllAddress(Specification<Address> spec, Pageable pageable);

    void handleDeleteAddress(Long id);
}

