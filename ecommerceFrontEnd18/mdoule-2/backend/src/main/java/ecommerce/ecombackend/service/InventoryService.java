package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.InventoryRequestDto;
import ecommerce.ecombackend.dto.responses.InventoryResponseDto;
import ecommerce.ecombackend.model.Product;
import ecommerce.ecombackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryResponseDto updateStock(InventoryRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int newStock = product.getStock() + dto.getQuantity();
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(newStock);
        Product updated = productRepository.save(product);

        return InventoryResponseDto.builder()
                .productId(updated.getId())
                .productName(updated.getName())
                .availableStock(updated.getStock())
                .build();
    }

    public InventoryResponseDto getStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return InventoryResponseDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .availableStock(product.getStock())
                .build();
    }
}
