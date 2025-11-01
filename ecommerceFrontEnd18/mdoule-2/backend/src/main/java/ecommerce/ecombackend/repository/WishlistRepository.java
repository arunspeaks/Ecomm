package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.Product;
import ecommerce.ecombackend.model.User;
import ecommerce.ecombackend.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem,Long>{
    List<WishlistItem>findByUserId(Long userId);
    Optional<WishlistItem>findByUserAndProduct(User user, Product product);
    void deleteByUserIdAndProductId(Long userId,Long productId);
    void deleteByUserId(Long userId);

}
