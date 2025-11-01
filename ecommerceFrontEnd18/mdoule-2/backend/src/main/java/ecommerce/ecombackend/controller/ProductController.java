package ecommerce.ecombackend.controller;

import ecommerce.ecombackend.dto.requests.ProductRequestDto;
import ecommerce.ecombackend.dto.responses.ProductResponseDto;
import ecommerce.ecombackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true",
        allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ProductController {

    private final ProductService productService;

    // Search products by keyword
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(
            @RequestParam String search) {
        List<ProductResponseDto> products = productService.searchProductsByKeyword(search);
        return ResponseEntity.ok(products);
    }
    // Create a new product (admin/seller only)
    @PostMapping("/admin")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto) {
        ProductResponseDto createdProduct = productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.getAllProducts(search, category, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
            @PathVariable String categoryName) {
        List<ProductResponseDto> products = productService.getProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/price/{priceRange}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByPriceRange(
            @PathVariable String priceRange) {
        // URL decode the price range (handles spaces and special characters)
        String decodedPriceRange = priceRange;
        List<ProductResponseDto> products = productService.getProductsByPriceRange(decodedPriceRange);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDto>> getFilteredProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String priceRange) {

        List<ProductResponseDto> products = productService.getFilteredProducts(search, category, priceRange);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/admin/{id}/update")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto dto) {
        ProductResponseDto updatedProduct = productService.updateProductPartial(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/cart/{id}")
    public ResponseEntity<ProductResponseDto> getCartDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/wishlist/{id}")
    public ResponseEntity<ProductResponseDto> getWishlistDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


}