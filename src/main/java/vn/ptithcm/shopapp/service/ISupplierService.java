package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Supplier;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.SupplierResponseDTO;

public interface ISupplierService {
    SupplierResponseDTO handleCreateSupplier(Supplier supplier);

    SupplierResponseDTO handleUpdateSupplier(Supplier supplier);

    SupplierResponseDTO handleFetchSupplierById(String id);

    PaginationResponseDTO handleFetchAllSuppliers(Specification<Supplier> spec, Pageable pageable);

    void handleDeleteSupplier(String id);
}
