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
        if(existBrandByName(brand.getName())) {
            throw new IdInvalidException("Brand with name "+brand.getName()+" already exist");
        }
        return this.brandRepository.save(brand);
    }

    @Override
    public Brand handleUpdateBrand(Brand brand) {
        Brand brandDB = handleFetchBrandById(brand.getId());

        brandDB.setName(brand.getName());

        return this.brandRepository.save(brandDB);
    }

    @Override
    public PaginationResponseDTO handldeFetchAllBrands(Specification<Brand> spec, Pageable pageable) {
        Page<Brand> brandPage = brandRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, brandPage);

        result.setResult(brandPage.getContent());

        return result;
    }

    @Override
    public void handleDeleteBrand(Long id) {
        Brand brand = handleFetchBrandById(id);
        if (!brand.getProducts().isEmpty()){
            throw new IdInvalidException("Brand cannot be deleted because there are products in the brand");
        }
        brandRepository.delete(brand);
    }

    boolean existBrandByName(String name){
        return brandRepository.existsByName(name);
    }
}
