package vn.ptithcm.shopapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.repository.PermissionRepository;
import vn.ptithcm.shopapp.repository.RoleRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j(topic = "INITIATION-SERVICE")
public class DatabaseInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public DatabaseInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args)  {
        log.info("Initializing Database...");

        long roleSize = roleRepository.count();
        long permissionSize = permissionRepository.count();
        long userSize = userRepository.count();


        if(permissionSize == 0){

            this.permissionRepository.saveAll(createAllPermission());

            log.info(">>>>>> Successful initiate permission" );
        }
        if (roleSize == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();

            adminRole.setCode(SecurityUtil.ROLE_ADMIN);

            adminRole.setName("Admin role");
            adminRole.setPermissions(allPermissions);

            Role customerRole = new Role();

            customerRole.setCode(SecurityUtil.ROLE_CUSTOMER);

            customerRole.setName("Customer role");

            List<Role> toCreateRoles = new ArrayList<>();
            toCreateRoles.add(adminRole);
            toCreateRoles.add(customerRole);

            this.roleRepository.saveAll(toCreateRoles);

            log.info(">>>>>> Successful initiate role" );

        }
        if(userSize == 0){
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setActive(true);
            adminUser.setPassword(this.passwordEncoder.encode(SecurityUtil.INITIAL_PASSWORD));
            adminUser.setFullName("I'M SYSTEM ADMIN");

            Role adminRole = this.roleRepository.findByCode(SecurityUtil.ROLE_ADMIN);
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            User customer = new User();
            customer.setEmail("customer@gmail.com");
            customer.setActive(true);
            customer.setPassword(this.passwordEncoder.encode(SecurityUtil.INITIAL_PASSWORD));
            customer.setFullName("I'M CUSTOMER USER");

            Role customerRole = this.roleRepository.findByCode(SecurityUtil.ROLE_CUSTOMER);
            if (customerRole != null) {
                customer.setRole(customerRole);
            }

            List<User> toCreateUsers = new ArrayList<>();
            toCreateUsers.add(customer);
            toCreateUsers.add(adminUser);

            this.userRepository.saveAll(toCreateUsers);
        }

        if (permissionSize > 0 && roleSize > 0 && userSize > 0) {
            log.info(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            log.info(">>> END INIT DATABASE");
    }

    public List<Permission> createAllPermission() {
        ArrayList<Permission> arr = new ArrayList<>();

        // Adding all permissions based on the provided data
        arr.add(createPermission("Fetch all users", "/api/v1/users", "GET", "User"));
        arr.add(createPermission("Update user", "/api/v1/users", "PUT", "User"));
        arr.add(createPermission("Create a new user", "/api/v1/users", "POST", "User"));
        arr.add(createPermission("Fetch all suppliers", "/api/v1/suppliers", "GET", "Supplier"));
        arr.add(createPermission("Update a supplier", "/api/v1/suppliers", "PUT", "Supplier"));
        arr.add(createPermission("Create a supplier", "/api/v1/suppliers", "POST", "Supplier"));
        arr.add(createPermission("Fetch all roles", "/api/v1/roles", "GET", "Role"));
        arr.add(createPermission("Update a role", "/api/v1/roles", "PUT", "Role"));
        arr.add(createPermission("Create a role", "/api/v1/roles", "POST", "Role"));
        arr.add(createPermission("Fetch all products", "/api/v1/products", "GET", "Product"));
        arr.add(createPermission("Update a product", "/api/v1/products", "PUT", "Product"));
        arr.add(createPermission("Create a product", "/api/v1/products", "POST", "Product"));
        arr.add(createPermission("Fetch all permissions", "/api/v1/permissions", "GET", "Permission"));
        arr.add(createPermission("Update a permission", "/api/v1/permissions", "PUT", "Permission"));
        arr.add(createPermission("Create a permission", "/api/v1/permissions", "POST", "Permission"));
        arr.add(createPermission("Fetch all orders", "/api/v1/orders", "GET", "Order"));
        arr.add(createPermission("Customer update an order", "/api/v1/orders", "PUT", "Order"));
        arr.add(createPermission("Customer place an order", "/api/v1/orders", "POST", "Order"));
        arr.add(createPermission("Fetch all categories", "/api/v1/categories", "GET", "Category"));
        arr.add(createPermission("Update a category", "/api/v1/categories", "PUT", "Category"));
        arr.add(createPermission("Create a category", "/api/v1/categories", "POST", "Category"));
        arr.add(createPermission("Get all products in cart", "/api/v1/carts", "GET", "Cart"));
        arr.add(createPermission("Update cart", "/api/v1/carts", "PUT", "Cart"));
        arr.add(createPermission("Add to cart", "/api/v1/carts", "POST", "Cart"));
        arr.add(createPermission("Fetch all brands", "/api/v1/brands", "GET", "Brand"));
        arr.add(createPermission("Update a brand", "/api/v1/brands", "PUT", "Brand"));
        arr.add(createPermission("Create a brand", "/api/v1/brands", "POST", "Brand"));
        arr.add(createPermission("Admin update an order", "/api/v1/admin-orders", "PUT", "Order"));
        arr.add(createPermission("Admin create an order", "/api/v1/admin-orders", "POST", "Order"));
        arr.add(createPermission("Fetch all address", "/api/v1/addresses", "GET", "Address"));
        arr.add(createPermission("Update a address", "/api/v1/addresses", "PUT", "Address"));
        arr.add(createPermission("Create a address", "/api/v1/addresses", "POST", "Address"));
        arr.add(createPermission("User forgot password", "/api/v1/users/forgot-pwd", "POST", "User"));
        arr.add(createPermission("Change user password", "/api/v1/auth/change-password", "POST", "Authentication"));
        arr.add(createPermission("Create payment URL", "/api/v1/payments", "POST", "Payment"));
        arr.add(createPermission("Download a file", "/api/v1/files", "GET", "File"));
        arr.add(createPermission("Upload a file", "/api/v1/files", "POST", "File"));
        arr.add(createPermission("Upload multiple images", "/api/v1/files/multiple", "POST", "File"));
        arr.add(createPermission("Process chat message", "/api/v1/chat", "POST", "Chat"));
        arr.add(createPermission("Check product quantity", "/api/v1/carts/check", "POST", "Cart"));
        arr.add(createPermission("Reset user password", "/api/v1/auth/reset-pwd", "POST", "Authentication"));
        arr.add(createPermission("Resend verification email", "/api/v1/auth/resend-verify-email", "POST", "Authentication"));
        arr.add(createPermission("User registration", "/api/v1/auth/register", "POST", "Authentication"));
        arr.add(createPermission("User logout", "/api/v1/auth/logout", "POST", "Authentication"));
        arr.add(createPermission("User login", "/api/v1/auth/login", "POST", "Authentication"));
        arr.add(createPermission("Fetch user by ID", "/api/v1/users/{id}", "GET", "User"));
        arr.add(createPermission("Delete a user", "/api/v1/users/{id}", "DELETE", "User"));
        arr.add(createPermission("Fetch user orders", "/api/v1/user-orders/{userId}", "GET", "Order"));
        arr.add(createPermission("Fetch a supplier", "/api/v1/suppliers/{id}", "GET", "Supplier"));
        arr.add(createPermission("Delete a supplier", "/api/v1/suppliers/{id}", "DELETE", "Supplier"));
        arr.add(createPermission("Fetch a role", "/api/v1/roles/{id}", "GET", "Role"));
        arr.add(createPermission("Delete a role", "/api/v1/roles/{id}", "DELETE", "Role"));
        arr.add(createPermission("Fetch a product", "/api/v1/products/{id}", "GET", "Product"));
        arr.add(createPermission("Get filter data", "/api/v1/products/filter", "GET", "Product"));
        arr.add(createPermission("Handle payment result", "/api/v1/payments/vnpay-payment-return", "GET", "Payment"));
        arr.add(createPermission("Fetch an order", "/api/v1/orders/{id}", "GET", "Order"));
        arr.add(createPermission("Fetch a category", "/api/v1/categories/{id}", "GET", "Category"));
        arr.add(createPermission("Delete a category", "/api/v1/categories/{id}", "DELETE", "Category"));
        arr.add(createPermission("Fetch a brand", "/api/v1/brands/{id}", "GET", "Brand"));
        arr.add(createPermission("Delete a brand", "/api/v1/brands/{id}", "DELETE", "Brand"));
        arr.add(createPermission("Verify user email", "/api/v1/auth/verify-email", "GET", "Authentication"));
        arr.add(createPermission("Refresh JWT tokens", "/api/v1/auth/refresh", "GET", "Authentication"));
        arr.add(createPermission("Get current user info", "/api/v1/auth/account", "GET", "Authentication"));
        arr.add(createPermission("Fetch a address", "/api/v1/addresses/{id}", "GET", "Address"));
        arr.add(createPermission("Delete a address", "/api/v1/addresses/{id}", "DELETE", "Address"));
        arr.add(createPermission("", "/", "GET", "Home"));
        arr.add(createPermission("Delete a permission", "/api/v1/permissions/{id}", "DELETE", "Permission"));
        arr.add(createPermission("Remove a product from cart", "/api/v1/carts/{productId}", "DELETE", "Cart"));
        arr.add(createPermission("Clear all cart", "/api/v1/carts/clear", "DELETE", "Cart"));

        return arr;
    }

    private Permission createPermission(String name, String apiPath, String method, String module) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setApiPath(apiPath);
        permission.setMethod(method);
        permission.setModule(module);
        return permission;
    }

}
