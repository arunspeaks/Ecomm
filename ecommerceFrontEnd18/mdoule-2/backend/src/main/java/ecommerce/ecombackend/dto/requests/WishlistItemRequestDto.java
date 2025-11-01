package ecommerce.ecombackend.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItemRequestDto {
    @NotNull(message = "User Id is required")
    private Long userId;
    @NotNull(message = "product Id is required")
    private Long productId;
    private  String size;
    @NotBlank(message = "Color must not be blank")
    private String color;

}
