package vn.ptithcm.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.enums.TokenTypeEnum;
import vn.ptithcm.shopapp.model.entity.Brand;
import vn.ptithcm.shopapp.model.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {

    void deleteByUserIdAndType(long userId, TokenTypeEnum type);
    Token findByUserIdAndType(long userId, TokenTypeEnum type);
}