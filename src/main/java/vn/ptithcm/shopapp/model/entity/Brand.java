package vn.ptithcm.shopapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand extends Base{
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
