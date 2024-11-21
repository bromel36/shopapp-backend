package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.MemoryTypeEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant updatedAt;

    private CategoryResponse category;

    private BrandResponse brand;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryResponse{
        private String id;
        private String name;
        private String code;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandResponse{
        private String id;
        private String name;
    }
}
