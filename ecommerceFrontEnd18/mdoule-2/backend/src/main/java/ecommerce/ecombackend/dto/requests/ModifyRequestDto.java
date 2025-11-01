package ecommerce.ecombackend.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyRequestDto {
    private String name;
    private String description;
    private double price;
    private int stock;
    private String imageUrl;
    private String category;
    private String brand;
    private String requestType;
}