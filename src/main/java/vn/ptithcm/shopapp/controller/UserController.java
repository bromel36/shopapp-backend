package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.ChangePasswordDTO;
import vn.ptithcm.shopapp.model.request.ForgotPasswordDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;
import vn.ptithcm.shopapp.validation.CreateUserValidationGroup;
import vn.ptithcm.shopapp.validation.UpdateUserValidationGroup;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.handleFetchUserResponseById(id));
    }


    @PostMapping("/users")
    @ApiMessage("create a new user")
    public ResponseEntity<UserResponseDTO> createUser(@Validated(CreateUserValidationGroup.class) @RequestBody User userRequest){
        UserResponseDTO userCreated = userService.handleCreateUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<PaginationResponseDTO> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable
    ){
        PaginationResponseDTO paginationResponseDTO = userService.handleGetAllUsers(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

    @PutMapping("/users")
    @ApiMessage("update user success")
    public ResponseEntity<UserResponseDTO> updateUser(@Validated(UpdateUserValidationGroup.class) @RequestBody User userRequest){
        UserResponseDTO userResponseDTO = this.userService.handleUpdateUser(userRequest);

        return ResponseEntity.ok(userResponseDTO);
    }

//    @DeleteMapping("/users/{id}")
//    @ApiMessage("deleted a user")
//    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) throws IdInvalidException {
//
//        this.userService.handleUserDelete(id);
//        return ResponseEntity.ok(null);
//    }
}
