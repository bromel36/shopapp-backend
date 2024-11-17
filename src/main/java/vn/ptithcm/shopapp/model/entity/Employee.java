package vn.ptithcm.shopapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.ptithcm.shopapp.enums.GenderEnum;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee extends Base {

    private String fullName;
    private String address;
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Instant birthday;

    private Double salary;

    private Instant hireDate;

    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<ImportOrder> importOrders;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InventoryLog> inventoryLogs;
}
