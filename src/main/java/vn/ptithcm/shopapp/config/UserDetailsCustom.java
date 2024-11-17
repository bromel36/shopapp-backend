package vn.ptithcm.shopapp.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.service.IUserService;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

    private final IUserService userService;

    public UserDetailsCustom(IUserService userService) {
        this.userService = userService;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.ptithcm.shopapp.model.entity.User myUser = this.userService.handleGetUserByUsername(username);
        if(myUser == null){
            throw new UsernameNotFoundException("username/password incorrect");
        }
        if(!myUser.getActive()){
            throw new IdInvalidException("User is inactive!!!");
        }
        return new User(
                myUser.getUsername(),
                myUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // role này tạo cho đủ cái constructorr này thôi chớ không có ý nghĩa
        );
    }

}
