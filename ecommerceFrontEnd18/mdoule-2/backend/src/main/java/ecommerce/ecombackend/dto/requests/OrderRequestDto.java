package ecommerce.ecombackend.dto.requests;

import ecommerce.ecombackend.enums.PaymentMode;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    private Long userId;
    private List<OrderItemRequestDto> items;
    private PaymentMode paymentMode;
}
