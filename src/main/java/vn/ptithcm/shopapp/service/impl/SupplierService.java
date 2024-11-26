package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.SupplierConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.entity.Supplier;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.SupplierResponseDTO;
import vn.ptithcm.shopapp.repository.SupplierRepository;
import vn.ptithcm.shopapp.service.IProductService;
import vn.ptithcm.shopapp.service.ISupplierService;
import vn.ptithcm.shopapp.util.PaginationUtil;

import java.util.List;

@Service
public class SupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;


    private final IProductService productService;
    private final SupplierConverter supplierConverter;

    public SupplierService(SupplierRepository supplierRepository, IProductService productService, SupplierConverter supplierConverter) {
        this.supplierRepository = supplierRepository;
        this.productService = productService;
        this.supplierConverter = supplierConverter;
    }


    @Override
    public SupplierResponseDTO handleCreateSupplier(Supplier supplier) {

        List<Product> products = supplier.getProducts();

        if (products != null && !products.isEmpty()) {
            List<String> productIds = products.stream().map(item -> item.getId()).toList();

            List<Product> productsDB = productService.handleFetchAllProductByIds(productIds);

            supplier.setProducts(productsDB);
        }
        supplierRepository.save(supplier);

        return supplierConverter.convertToSupplierResponseDTO(supplier);
    }

    @Override
    public SupplierResponseDTO handleUpdateSupplier(Supplier supplier) {
        Supplier supplierDB = supplierRepository.findById(supplier.getId())
                .orElseThrow(() -> new IdInvalidException("Supplier does not exist"));

        List<Product> products = supplier.getProducts();

        if (products != null && !products.isEmpty()) {
            List<String> productIds = products.stream().map(item -> item.getId()).toList();

            List<Product> productsDB = productService.handleFetchAllProductByIds(productIds);

            supplierDB.setProducts(productsDB);
        }

        supplierDB.setName(supplier.getName());
        supplierDB.setAddress(supplier.getAddress());
        supplierDB.setPhone(supplier.getPhone());
        supplierDB.setEmail(supplier.getEmail());

        supplierRepository.save(supplierDB);

        return supplierConverter.convertToSupplierResponseDTO(supplierDB);

    }

    @Override
    public SupplierResponseDTO handleFetchSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Supplier does not exist"));

        return supplierConverter.convertToSupplierResponseDTO(supplier);
    }

    @Override
    public PaginationResponseDTO handleFetchAllSuppliers(Specification<Supplier> spec, Pageable pageable) {
        Page<Supplier> supplierPage = supplierRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, supplierPage);

        List<SupplierResponseDTO> dtos = supplierPage.getContent().stream().map(supplierConverter::convertToSupplierResponseDTO).toList();

        result.setResult(dtos);

        return result;
    }

    @Override
    public void handleDeleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Supplier does not exist"));

        if (supplier.getImportOrders() != null && !supplier.getImportOrders().isEmpty()) {
            throw new IdInvalidException("Supplier has already existed import orders");
        }

        supplierRepository.delete(supplier);
    }
}
