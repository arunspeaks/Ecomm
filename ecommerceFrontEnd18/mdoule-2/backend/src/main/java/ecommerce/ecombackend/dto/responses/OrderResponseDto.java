package ecommerce.ecombackend.dto.responses;

import ecommerce.ecombackend.dto.OrderItemDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private String paymentMode;
    private String paymentStatus;
    private Double totalAmount;
    private Long userId;
    private List<OrderItemDto> items;
}