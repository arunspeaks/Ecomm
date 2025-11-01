package ecommerce.ecombackend.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItemResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String size;
    private String color;
    private String imageurl;
    private LocalDateTime addedAt;
}
