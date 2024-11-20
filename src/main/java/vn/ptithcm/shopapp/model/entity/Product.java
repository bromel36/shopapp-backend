package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.MemoryTypeEnum;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends Base {
    private String name;
    private String brand;
    private String model;
    private String cpu;
    private Integer ram;
    private String memory;

    @Enumerated(EnumType.STRING)
    private MemoryTypeEnum memoryType;

    private String gpu;
    private Double screen;
    private Boolean touch;
    private Double price;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String thumbnail;

    private Boolean status;

    private Double weight;
    private Integer quantity;

    private Integer sold;

    private String tag;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "products",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductInstance> productInstances;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImportOrderDetail> importOrderDetails;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cart> carts;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InventoryLog> inventoryLogs;
}
