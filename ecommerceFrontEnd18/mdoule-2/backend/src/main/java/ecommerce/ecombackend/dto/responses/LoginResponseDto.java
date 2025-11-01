package ecommerce.ecombackend.dto.responses;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String message;
    private String token;
    private UserResponseDto user;

}
