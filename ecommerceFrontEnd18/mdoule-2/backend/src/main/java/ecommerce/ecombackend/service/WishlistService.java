package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.WishlistItemRequestDto;
import ecommerce.ecombackend.dto.responses.WishlistItemResponseDto;
import ecommerce.ecombackend.model.CartItem;
import ecommerce.ecombackend.model.Product;
import ecommerce.ecombackend.model.User;
import ecommerce.ecombackend.model.WishlistItem;
import ecommerce.ecombackend.repository.CartRepository;
import ecommerce.ecombackend.repository.ProductRepository;
import ecommerce.ecombackend.repository.UserRepository;
import ecommerce.ecombackend.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CartRepository cartRepo;

//    Add to wishlist

    public String addwishlist(WishlistItemRequestDto dto) {
        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepo.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("product not found"));

        Optional<WishlistItem> existing = wishlistRepo.findByUserAndProduct(user, product);

        if (existing.isPresent()) {
            return "Product is already in wishlist.";
        } else {

            WishlistItem item = WishlistItem.builder()
                    .user(user)
                    .product(product)
                    .size(dto.getSize())
                    .color(dto.getColor())
                    .addedAt(LocalDateTime.now())
                    .build();
            wishlistRepo.save(item);
            return "product added to wishlist";
        }
    }
      //   Get all wishlistitems

        public List<WishlistItemResponseDto> getWishlistItems(Long userId) {
            List<WishlistItem> items = wishlistRepo.findByUserId(userId);

            return  items.stream().map(item ->WishlistItemResponseDto.builder()
                    .id(item.getId())
                    .userId(userId)
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .imageurl(item.getProduct().getImageUrl())
                    .size(item.getSize())
                    .color(item.getColor())
                    .addedAt(item.getAddedAt())
                    .build()
            ).collect(Collectors.toList());
    }
    // Remove product from wishlist
@Transactional
    public String remove(Long userId,Long productId){
        wishlistRepo.deleteByUserIdAndProductId(userId,productId);
        return "product removed from wishlist";
    }

    @Transactional
    public String moveToCart(Long wishlistItemId) {
        // 1. Find wishlist item
        WishlistItem wishlistItem = wishlistRepo.findById(wishlistItemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        // 2. Create CartItem object
        CartItem cartItem = CartItem.builder()
                .user(wishlistItem.getUser())
                .product(wishlistItem.getProduct())
                .size(wishlistItem.getSize())
                .color(wishlistItem.getColor())
                .quantity(1) // default quantity (can be changed later)
                .addedAt(LocalDateTime.now())
                .build();

        // 3. Save cart item
        cartRepo.save(cartItem);

        // 4. Remove wishlist item
        wishlistRepo.delete(wishlistItem);

        return "Product moved to cart successfully.";
    }


    //    clear entire wishlist
    @Transactional
    public String clear(Long userId){
        wishlistRepo.deleteByUserId(userId);
        return "All wishlist items removed.";
    }
}
