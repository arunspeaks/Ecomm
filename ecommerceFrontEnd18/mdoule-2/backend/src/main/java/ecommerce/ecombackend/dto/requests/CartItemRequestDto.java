package ecommerce.ecombackend.dto.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Product ID is required")
    private Long productId;
    @Min(value=1,message = "Quantity must be at least 1")
    private int quantity;
    private String size;
    @NotBlank(message = "Color must not be blank")
    private String color;
}
