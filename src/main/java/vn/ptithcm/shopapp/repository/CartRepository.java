package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.model.entity.Cart;
import vn.ptithcm.shopapp.model.entity.Product;
import vn.ptithcm.shopapp.model.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    Cart findCartByProductAndUser(Product product, User user);

    List<Cart> findAllByUser(User userDB);

    void deleteByProductAndUser(Product product, User userDB);


    void deleteAllByUser(User user);
}