package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "import_orders")
@Getter
@Setter
public class ImportOrder extends Base {

    private Instant date;

    private Integer total;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "importOrder",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductInstance> productInstances;

    @OneToMany(mappedBy = "importOrder",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImportOrderDetail> importOrderDetails;

    @OneToMany(mappedBy = "importOrder",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InventoryLog> inventoryLogs;
}
