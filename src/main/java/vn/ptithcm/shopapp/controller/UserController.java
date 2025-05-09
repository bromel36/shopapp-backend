package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Address;
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
@Tag(name = "User")
public class UserController {
    private final IUserService userService;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public UserController(IUserService userService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.userService = userService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
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
            @Parameter(
                    description = "Filtering expression (e.g., id:'1')",
                    example = "id:'1'"
            )
            @RequestParam(name = "filter", required = false) String filter,

            @ParameterObject Pageable pageable) {
        Specification<User> spec = filter == null ? null : filterSpecificationConverter.convert(filterParser.parse(filter));
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

    @DeleteMapping("/users/{id}")
    @ApiMessage("deleted a user")
    @Operation(summary = "Delete a user", description = "Delete a user by their ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        this.userService.handleUserDelete(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/users/forgot-pwd")
    @ApiMessage("send verify email successfully")
    @Operation(summary = "User forgot password", description = "For user forgot password and reset their password")
    public ResponseEntity<Void> forgotPassword(
            @Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO,
            @RequestHeader(value = "X-Client-Type", defaultValue = "WEB") String clientType) {
        this.userService.handleForgotPassword(forgotPasswordDTO, clientType);
        return ResponseEntity.ok(null);
    }
}
