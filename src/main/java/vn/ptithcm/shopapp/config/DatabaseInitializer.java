package vn.ptithcm.shopapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.entity.Permission;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.repository.EmployeeRepository;
import vn.ptithcm.shopapp.repository.PermissionRepository;
import vn.ptithcm.shopapp.repository.RoleRepository;
import vn.ptithcm.shopapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    private final String INITIAL_PASSWORD = "123456";
    private final String INITIAL_ROLE = "SUPER_ADMIN";

    public DatabaseInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing Database...");

        Long roleSize = roleRepository.count();
        Long permissionSize = permissionRepository.count();
        Long userSize = userRepository.count();


//        if(permissionSize == 0){
//
//            this.permissionRepository.saveAll(createAllPermission());
//
//            System.out.println(">>>>>> Successful initiate permission" );
//        }
        if (roleSize == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();

            adminRole.setCode("SUPER_ADMIN");

            adminRole.setName("Admin role - full permissions");
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);

            System.out.println(">>>>>> Successful initiate role" );

        }
        if(userSize == 0){
            User adminUser = new User();
            adminUser.setUsername("admin@gmail.com");
            adminUser.setActive(true);
            adminUser.setPassword(this.passwordEncoder.encode(INITIAL_PASSWORD));

            Role adminRole = this.roleRepository.findByCode(INITIAL_ROLE);
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);

            if(employeeRepository.findByUserId(adminUser.getId()) == null){
                Employee employee = new Employee();
                employee.setFullName("I AM SUPER ADMIN");
                employee.setUser(adminUser);

                employeeRepository.save(employee);
            }
        }

        if (permissionSize > 0 && roleSize > 0 && userSize > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");

    }

    public List<Permission> createAllPermission(){
        ArrayList<Permission> arr = new ArrayList<>();


        return arr;
    }

}
