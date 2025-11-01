package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.ProductRequestDto;
import ecommerce.ecombackend.dto.responses.ProductResponseDto;
import ecommerce.ecombackend.model.Product;
import ecommerce.ecombackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Helper: Convert Product entity to ProductResponseDto
    private ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .brand(product.getBrand())
                .build();
    }

    // Helper: Convert ProductRequestDto to Product entity
    private Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .category(dto.getCategory())
                .brand(dto.getBrand())
                .build();
    }

    // Search products by name only
    public List<ProductResponseDto> searchProductsByKeyword(String search) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(search);
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    // Create a new product

    public ProductResponseDto addProduct(ProductRequestDto dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    // Get products with optional filters

    public List<ProductResponseDto> getAllProducts(String search, String category, Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream().filter(p -> {
            boolean matches = true;
            if (search != null && !p.getName().toLowerCase().contains(search.toLowerCase()))
                matches = false;
            if (category != null && !p.getCategory().equalsIgnoreCase(category))
                matches = false;
            if (minPrice != null && p.getPrice() < minPrice)
                matches = false;
            if (maxPrice != null && p.getPrice() > maxPrice)
                matches = false;
            return matches;
        }).map(this::toDto).collect(Collectors.toList());
    }

    // Get product by ID

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDto(product);
    }

    // List all categories
    // New method - Get products by category
    public List<ProductResponseDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List all prices (as strings for display/filtering)

    public List<ProductResponseDto> getProductsByPriceRange(String priceRange) {
        List<Product> products = filterByPriceRange(priceRange);
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public List<ProductResponseDto> getFilteredProducts(String search, String category, String priceRange) {
        Double[] priceRangeParsed = parsePriceRange(priceRange);
        Double minPrice = priceRangeParsed[0];
        Double maxPrice = priceRangeParsed[1];

        List<Product> products = productRepository.findProductsWithFilters(
                search, category, minPrice, maxPrice);

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    private Double[] parsePriceRange(String priceRange) {
        if (priceRange == null || priceRange.equals("All Prices")) {
            return new Double[]{null, null};
        }

        switch (priceRange) {
            case "0-25":
                return new Double[]{0.0, 25.0};
            case "25-50":
                return new Double[]{26.0, 50.0};
            case "50-100":
                return new Double[]{51.0, 100.0};
            case "100-500":
                return new Double[]{101.0, 500.0};
            case "500-1000":
                return new Double[]{501.0, 1000.0};
            case "1000+":
                return new Double[]{1001.0, Double.MAX_VALUE};
            default:
                return new Double[]{null, null};
        }
    }

    // Helper method to parse price range and filter
    private List<Product> filterByPriceRange(String priceRange) {
        switch (priceRange) {
            case "0-25":
                return productRepository.findByPriceBetween(0.0, 25.0);
            case "25-50":
                return productRepository.findByPriceBetween(26.0, 50.0);
            case "50-100":
                return productRepository.findByPriceBetween(51.0, 100.0);
            case "100-500":
                return productRepository.findByPriceBetween(101.0, 500.0);
            case "500-1000":
                return productRepository.findByPriceBetween(501.0, 1000.0);
            case "1000+":
                return productRepository.findByPriceGreaterThanEqual(1001.0);
//            case "All Prices":
//                return productRepository.findAll();
              default:
               return productRepository.findAll();
        }
    }

    // Partially update product fields (does not require all fields)

    public ProductResponseDto updateProductPartial(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() >= 0) product.setPrice(dto.getPrice());
        if (dto.getStock() >= 0) product.setStock(dto.getStock());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getBrand() != null) product.setBrand(dto.getBrand());

        Product updated = productRepository.save(product);
        return toDto(updated);
    }

    // Delete a product

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

