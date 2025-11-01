package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.WishlistItemRequestDto;
import ecommerce.ecombackend.dto.responses.WishlistItemResponseDto;
import ecommerce.ecombackend.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistItemController {

    @Autowired
    private WishlistService service;

    @PostMapping("/add")
    public ResponseEntity<String> addwishlist(@Valid @RequestBody WishlistItemRequestDto dto) {
        String result = service.addwishlist(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WishlistItemResponseDto>> getWishlistItems(@PathVariable Long userId) {
        List<WishlistItemResponseDto> items = service.getWishlistItems(userId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<String> remove(@PathVariable Long userId, @PathVariable Long productId) {
        String messsage = service.remove(userId, productId);
        return ResponseEntity.ok(messsage);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clear(@PathVariable Long userId) {
        String message = service.clear(userId);
        return ResponseEntity.ok(message);
    }

    // ✅ New API: Move wishlist item to cart
    @PostMapping("/move-to-cart/{wishlistItemId}")
    public ResponseEntity<String> moveToCart(@PathVariable Long wishlistItemId) {
        try {
            String message = service.moveToCart(wishlistItemId);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to move item: " + e.getMessage());
        }
    }
}
