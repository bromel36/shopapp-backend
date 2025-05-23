package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.ChangePasswordDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;
import vn.ptithcm.shopapp.validation.CreateUserValidationGroup;
import vn.ptithcm.shopapp.validation.UpdateUserValidationGroup;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "User")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    @Operation(summary = "Fetch user by ID", description = "Retrieve user details by their ID.")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.handleFetchUserResponseById(id));
    }

    @PostMapping("/users")
    @ApiMessage("create a new user")
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user details.")
    public ResponseEntity<UserResponseDTO> createUser(
            @Validated(CreateUserValidationGroup.class) @RequestBody User userRequest) {
        UserResponseDTO userCreated = userService.handleCreateUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    @Operation(summary = "Fetch all users", description = "Retrieve a paginated list of all users with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable) {
        PaginationResponseDTO paginationResponseDTO = userService.handleGetAllUsers(spec, pageable);
        return ResponseEntity.ok(paginationResponseDTO);
    }

    @PutMapping("/users")
    @ApiMessage("update user success")
    @Operation(summary = "Update user", description = "Update an existing user's details.")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Validated(UpdateUserValidationGroup.class) @RequestBody User userRequest) {
        UserResponseDTO userResponseDTO = this.userService.handleUpdateUser(userRequest);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/users/change-password")
    @ApiMessage("change password success")
    @Operation(summary = "Change password", description = "Change the password of the currently logged-in user.")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        this.userService.handleChangePassword(changePasswordDTO);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("deleted a user")
    @Operation(summary = "Delete a user", description = "Delete a user by their ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        this.userService.handleUserDelete(id);
        return ResponseEntity.ok(null);
    }
}
