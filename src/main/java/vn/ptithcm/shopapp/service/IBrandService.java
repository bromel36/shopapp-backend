package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IBrandService {

    Brand handleFetchBrandById(Long id);

    Brand handleCreateBrand(Brand brand);

    Brand handleUpdateBrand(Brand brand);

    PaginationResponseDTO handldeFetchAllBrands(Specification<Brand> spec, Pageable pageable);
}
