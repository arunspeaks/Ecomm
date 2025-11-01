package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.AddressRequestDto;
import ecommerce.ecombackend.dto.responses.AddressResponseDto;
import ecommerce.ecombackend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Create
    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto dto) {
        return ResponseEntity.ok(addressService.addAddress(dto));
    }

    // Read
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDto>> getUserAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<AddressResponseDto> getDefaultUserAddress(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getDefaultAddressByUserId(userId));
    }

    // Update
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequestDto dto) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, dto));
    }

    @PatchMapping("/{addressId}/set-default")
    public ResponseEntity<AddressResponseDto> setDefaultAddress(
            @PathVariable Long addressId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(addressService.setDefaultAddress(userId, addressId));
    }

    // Delete
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}