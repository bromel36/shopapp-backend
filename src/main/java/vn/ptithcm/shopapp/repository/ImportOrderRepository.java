package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptithcm.shopapp.model.entity.ImportOrder;

import java.util.List;

public interface ImportOrderRepository extends JpaRepository<ImportOrder, Long>, JpaSpecificationExecutor<ImportOrder> {
    List<ImportOrder> findByIdIn(List<String> importOderIds);
}