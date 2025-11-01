package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.UserProfileUpdateDto;

import ecommerce.ecombackend.dto.responses.*;
import ecommerce.ecombackend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    // 1. Get user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 2. Update user profile (name, phone)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id,
                                                         @RequestBody UserProfileUpdateDto dto) {
        return ResponseEntity.ok(userService.updateProfile(id, dto));
    }

    // 3. Delete (deactivate) user account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User account deactivated.");
    }

    // 4. Change password
//    @PutMapping("/{id}/change-password")
//    public ResponseEntity<String> changePassword(@PathVariable Long id,
//                                                 @RequestParam String oldPassword,
//                                                 @RequestParam String newPassword) {
//        userService.changePassword(id, oldPassword, newPassword);
//        return ResponseEntity.ok("Password updated successfully.");
//    }
//    }
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@PathVariable Long id,
                                                              @RequestParam String oldPassword,
                                                              @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(Map.of("message", "Password updated successfully."));
    }



    // 6. Get addresses for a user
    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<AddressResponseDto>> getUserAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserAddresses(id));
    }


}
