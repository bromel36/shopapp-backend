package vn.ptithcm.shopapp.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.userService.handleFetchUserById(id));
    }



    @PostMapping("/users")
    @ApiMessage("create a new user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody User userRequest){
        UserResponseDTO userCreated = userService.handleCreateUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

//    @GetMapping("/users")
//    @ApiMessage("fetch all users")
//    public ResponseEntity<PaginationResponseDTO> getAllUsers(
//            @Filter Specification<User> spec,
//            Pageable pageable
//    ){
//        PaginationResponseDTO paginationResponseDTO = userService.handleGetAllUsers(spec, pageable);
//        return ResponseEntity.ok(paginationResponseDTO);
//    }

    @PutMapping("/users")
    @ApiMessage("update user success")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody User userRequest){
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
