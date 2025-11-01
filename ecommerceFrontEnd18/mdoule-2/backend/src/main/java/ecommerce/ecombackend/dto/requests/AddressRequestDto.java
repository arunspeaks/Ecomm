package ecommerce.ecombackend.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDto {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    @JsonProperty("isDefault")
    private boolean isDefault;

    private Long userId;
}
