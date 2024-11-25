package vn.ptithcm.shopapp.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.MemoryTypeEnum;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product{

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;
    private String name;
    private String model;
    private String cpu;
    private Integer ram;
    private String memory;

    @Enumerated(EnumType.STRING)
    private MemoryTypeEnum memoryType;

    private String gpu;
    private String screen;
    private Double price;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String thumbnail;
    private String slider;

    private Boolean status;

    private Double weight;
    private Integer quantity;
    private String color;
    private String port;
    private String os;

    private Integer sold;

    private String tag;


    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

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


    @Transient
    private Boolean shouldUpdateAudit = true;

    @PrePersist
    public void handleBeforeInsert(){
        createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get()
                : "";
        createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate(){
        if (shouldUpdateAudit){
            updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                    SecurityUtil.getCurrentUserLogin().get()
                    : "";
            updatedAt = Instant.now();
        }
    }

}
