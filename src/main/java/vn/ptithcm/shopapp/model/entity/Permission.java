package vn.ptithcm.shopapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission extends Base {

    private String name;

    private String apiPath;

    private String method;

    private String module;

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Role> roles;
}


