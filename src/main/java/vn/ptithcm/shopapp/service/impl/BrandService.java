package vn.ptithcm.shopapp.service.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.repository.BrandRepository;
import vn.ptithcm.shopapp.service.IBrandService;


@Service
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand handleFetchBrandById(String id) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Brand with id "+id+" not found"));
        return brand;
    }
}
