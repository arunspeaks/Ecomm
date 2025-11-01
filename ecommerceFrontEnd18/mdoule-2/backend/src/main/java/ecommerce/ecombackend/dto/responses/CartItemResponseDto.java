package ecommerce.ecombackend.dto.responses;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private int quantity;
    private String size;
    private String color;
    private String  imageUrl;
    private double totalPrice;
    private LocalDateTime addedAt;
}
