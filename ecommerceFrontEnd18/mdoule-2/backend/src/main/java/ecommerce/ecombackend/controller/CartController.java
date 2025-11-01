package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.CartItemRequestDto;
import ecommerce.ecombackend.dto.responses.CartItemResponseDto;
import ecommerce.ecombackend.dto.responses.OrderResponseDto;
import ecommerce.ecombackend.service.CartService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cart")

public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDto> addToCart(@Valid @RequestBody CartItemRequestDto dto) {
        CartItemResponseDto message = cartService.addToCart(dto);
        return ResponseEntity.ok(message);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable Long userId) {
        List<CartItemResponseDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatecartItem(@PathVariable Long id, @Valid @RequestBody CartItemRequestDto dto) {
        String message = cartService.updatecartItem(id, dto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<String> removeproduct(@PathVariable Long userId, @PathVariable Long productId) {
        String message = cartService.removeproduct(userId, productId);
        return ResponseEntity.ok(message);
    }


    @Transactional
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        String message = cartService.clearCart(userId);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/place-order/{cartItemId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long cartItemId) {
        try {
            String message = cartService.placeOrderForCartItem(cartItemId);
            return ResponseEntity.ok(message); // 200 OK with message
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to place order: " + e.getMessage());
        }

    }
}
