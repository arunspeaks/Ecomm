package ecommerce.ecombackend.dto.responses;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    @JsonProperty("isDefault")
    private boolean isDefault;
    private Long userId;
}
