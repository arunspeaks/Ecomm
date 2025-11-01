package ecommerce.ecombackend.dto.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {
    private Long productId;
    private String productName;
    private int availableStock;
}
