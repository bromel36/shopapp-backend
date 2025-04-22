package vn.ptithcm.shopapp.service.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.model.entity.Category;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.BrandRepository;
import vn.ptithcm.shopapp.service.IBrandService;
import vn.ptithcm.shopapp.util.PaginationUtil;


@Service
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand handleFetchBrandById(Long id) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Brand with id "+id+" not found"));
        return brand;
    }

    @Override
    public Brand handleCreateBrand(Brand brand) {
        return null;
    }

    @Override
    public Brand handleUpdateBrand(Brand brand) {
        return null;
    }

    @Override
    public PaginationResponseDTO handldeFetchAllBrands(Specification<Brand> spec, Pageable pageable) {
        Page<Brand> brandPage = brandRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, brandPage);

        result.setResult(brandPage.getContent());

        return result;
    }
}
