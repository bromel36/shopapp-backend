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

    @Query(value = "SELECT DISTINCT p.cpu FROM products p ORDER BY p.sold DESC LIMIT 7", nativeQuery = true)
    List<String> findTop7DistinctCpuOrderBySoldDesc();

    @Query(value = "SELECT DISTINCT p.gpu FROM products p ORDER BY p.sold DESC LIMIT 7", nativeQuery = true)
    List<String> findTop7DistinctGpuOrderBySoldDesc();

    @Query(value = "SELECT DISTINCT p.ram FROM products p ORDER BY p.sold DESC LIMIT 7", nativeQuery = true)
    List<String> findTop7DistinctRamOrderBySoldDesc();

    @Query(value = "SELECT DISTINCT p.model FROM products p ORDER BY p.sold DESC LIMIT 7", nativeQuery = true)
    List<String> findTop7DistinctModelOrderBySoldDesc();

    @Query(value = "SELECT DISTINCT p.screen FROM products p ORDER BY p.sold DESC LIMIT 7", nativeQuery = true)
    List<String> findTop7DistinctScreenOrderBySoldDesc();
}
