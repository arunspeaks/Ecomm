package ecommerce.ecombackend.repository;


import ecommerce.ecombackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all products by category (case-insensitive)
    List<Product> findByCategoryIgnoreCase(String category);

    // Search products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String search);
    // Get all unique prices (as String, can be changed to Double/BigDecimal)
    @Query("SELECT DISTINCT CAST(p.price AS string) FROM Product p ORDER BY p.price ASC")
    List<String> findAllPrices();

    // Example: Find all products in a price range
    List<Product> findByPriceBetween(Double min, Double max);
    List<Product> findByPriceGreaterThanEqual(Double minPrice);
    // Optional: add other filters as needed
    // Combined filtering method
    @Query("SELECT p FROM Product p " +
            "WHERE " +
            // Step 1: Category filter first
            "(:category IS NULL OR :category = 'All Categories' OR LOWER(p.category) = LOWER(:category)) " +
            "AND " +
            // Step 2: Price range filter
            "(:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND " +
            // Step 3: Search keyword filter
            "(:search IS NULL OR :search = '' OR " +
            " LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Product> findProductsWithFilters(
            @Param("search") String search,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
