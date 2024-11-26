package vn.ptithcm.shopapp.model.request;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.MemoryTypeEnum;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
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

    private Boolean status;

    private Double weight;
    private Integer quantity;
    private String color;
    private String port;
    private String os;

    private Integer sold;

    private String tag;

    private List<String> slider;

    @Valid
    @NotNull(message = "Category is required")
    CategoryRequest category;

    @Valid
    @NotNull(message = "Brand is required")
    BrandRequest brand;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryRequest{
        @NotNull(message = "Category is required")
        private Long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandRequest{
        @NotNull(message = "Brand is required")
        private Long id;
    }
}
