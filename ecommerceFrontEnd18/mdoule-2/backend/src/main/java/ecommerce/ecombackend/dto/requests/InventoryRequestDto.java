package ecommerce.ecombackend.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDto {
    private Long productId;
    private int quantity; // positive for increase, negative for decrease
}
