package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.CartItemRequestDto;
import ecommerce.ecombackend.dto.responses.CartItemResponseDto;
import ecommerce.ecombackend.enums.OrderStatus;
import ecommerce.ecombackend.enums.PaymentMode;
import ecommerce.ecombackend.enums.PaymentStatus;
import ecommerce.ecombackend.model.*;
import ecommerce.ecombackend.repository.*;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderItemRepository orderItemRepo;

    //    Create or update cartItem
    public CartItemResponseDto addToCart(CartItemRequestDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> optional = cartRepo.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());

        if (optional.isPresent()) {
            CartItem item = optional.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
            item.setSize(dto.getSize());
            item.setColor(dto.getColor());
            cartRepo.save(item);

            CartItemResponseDto result  = CartItemResponseDto.builder()
                    .id(item.getId())
                    .userId(item.getUser().getId())
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .size(item.getSize())
                    .color(item.getColor())
                    .imageUrl(item.getProduct().getImageUrl())
                    .totalPrice(item.getProduct().getPrice())
                    .addedAt(item.getAddedAt())
                    .build();

            return result;
        } else {
            CartItem item = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .size(dto.getSize())
                    .color(dto.getColor())
                    .addedAt(LocalDateTime.now())
                    .build();
            cartRepo.save(item);

            CartItemResponseDto result  = CartItemResponseDto.builder()
                    .id(item.getId())
                    .userId(item.getUser().getId())
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .size(item.getSize())
                    .color(item.getColor())
                    .imageUrl(item.getProduct().getImageUrl())
                    .totalPrice(item.getProduct().getPrice())
                    .addedAt(item.getAddedAt())
                    .build();
            return result;

        }

    }

    public List<CartItemResponseDto> getCartItems(Long userId) {
        List<CartItem> items = cartRepo.findByUserId(userId);

        return items.stream().map(item ->
                CartItemResponseDto.builder()
                        .id(item.getId())
                        .userId(userId)
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .size(item.getSize())
                        .color(item.getColor())
                        .imageUrl(item.getProduct().getImageUrl())
                        .totalPrice(item.getProduct().getPrice() * item.getQuantity())
                        .addedAt(item.getAddedAt())
                        .build()
        ).collect(Collectors.toList());
    }

    //    update cart item
    public String updatecartItem(Long cartItemId, CartItemRequestDto dto) {
        CartItem item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(dto.getQuantity());
        item.setSize(dto.getSize());
        item.setColor(dto.getColor());
        cartRepo.save(item);
        return "Cart item updated sucessfully.";
    }

    //    remove product from cart
    public String removeproduct(Long userId, Long productId) {
        Optional<CartItem> optional = cartRepo.findByUserIdAndProductId(userId, productId);
        if (optional.isPresent()) {
            cartRepo.delete(optional.get());
            return "product removed from cart Successfully";
        } else {
            return "Product not found in the cart for the user";
        }
    }


    //  Delete the cart Item
    @Transactional
    public String clearCart(Long userId) {
        cartRepo.deleteByUserId(userId);
        return "Item removed from cart.";
    }


    @Transactional
    public String placeOrderForCartItem(Long cartItemId) {
        CartItem cartItem = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Create Order
        Order order = Order.builder()
                .user(cartItem.getUser())
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING) // or PLACED
                .paymentMode(PaymentMode.COD) // or from request
                .paymentStatus(PaymentStatus.PENDING)
                .totalAmount(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .build();

        orderRepo.save(order);

        // Create OrderItem
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice())
                .size(cartItem.getSize())   // requires new field
                .color(cartItem.getColor()) // requires new field
                .build();

        orderItemRepo.save(orderItem);

        // Remove item from cart
        cartRepo.delete(cartItem);

        return "Order placed successfully for item: " + cartItem.getProduct().getName();
    }
}
