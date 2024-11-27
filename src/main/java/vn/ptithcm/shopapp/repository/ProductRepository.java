package vn.ptithcm.shopapp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptithcm.shopapp.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findByIdIn(List<String> ids);

    @Query("SELECT p FROM Product p WHERE "
            + "(:tag IS NULL OR p.tag LIKE %:tag%) "
            + "AND (:brand IS NULL OR p.brand.name LIKE %:brand%) "
            + "AND (:price IS NULL OR p.price <= :price) "
            + "ORDER BY p.sold DESC")
    List<Product> findByCriteria(@Param("tag") String tag, @Param("brand") String brand, @Param("price") Double price, Pageable pageable);


}
