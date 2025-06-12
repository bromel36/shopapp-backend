package vn.ptithcm.shopapp.controller;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.model.entity.Address;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.request.AddressRequestDTO;
import vn.ptithcm.shopapp.model.response.AddressResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.service.IAddressService;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Address")
public class AddressController {
    private final IAddressService addressService;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public AddressController(IAddressService addressService, FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
        this.addressService = addressService;
        this.filterParser = filterParser;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @PostMapping("/addresses")
    @ApiMessage("created address successfully")
    @Operation(summary = "Create a address", description = "Create a new address and return the created address details.")
    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.handleCreateAddress(address));
    }

    @PutMapping("/addresses")
    @ApiMessage("updated address successfully")
    @Operation(summary = "Update a address", description = "Update an existing address and return the updated address details.")
    public ResponseEntity<AddressResponseDTO> updateAddress(@Valid @RequestBody AddressRequestDTO address) {
        return ResponseEntity.ok().body(addressService.handleUpdateAddress(address));
    }

    @GetMapping("addresses/{id}")
    @ApiMessage("fetch a address")
    @Operation(summary = "Fetch a address", description = "Fetch details of a address by its ID.")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressService.handleFetchAddressResponseById(id));
    }


    @GetMapping("/addresses")
    @ApiMessage("fetch all address")
    @Operation(summary = "Fetch all address", description = "Fetch a paginated list of all address with optional filtering.")
    public ResponseEntity<PaginationResponseDTO> getAllAddress(
            @Parameter(
                    description = "Filtering expression (e.g., id:'1')",
                    example = "id:'1'"
            )
            @RequestParam(name = "filter", required = false) String filter,

            @ParameterObject Pageable pageable) {
        Specification<Address> spec = filter == null ? Specification.where(null) : filterSpecificationConverter.convert(filterParser.parse(filter));
        PaginationResponseDTO paginationResponseDTO = this.addressService.handldeFetchAllAddress(spec, pageable);

        return ResponseEntity.ok(paginationResponseDTO);
    }

    @DeleteMapping("addresses/{id}")
    @ApiMessage("delete a address")
    @Operation(summary = "Delete a address", description = "Delete a address by its ID from DB.")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        addressService.handleDeleteAddress(id);
        return ResponseEntity.ok(null);
    }
}
