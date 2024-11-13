package vn.ptithcm.shopapp.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.ptithcm.shopapp.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByRefreshTokenAndUsername(String refreshToken, String username);

    boolean existsByUsername(String username);
}
