package vn.ptithcm.shopapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.repository.custom.ProductRepositoryCustom;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
    List<Product> findByIdIn(List<String> ids);
}
