package ecommerce.ecombackend.dto.responses;



import lombok.*;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
}
