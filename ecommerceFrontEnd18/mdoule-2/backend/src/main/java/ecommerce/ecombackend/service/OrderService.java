
package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.OrderItemDto;
import ecommerce.ecombackend.dto.requests.OrderRequestDto;
import ecommerce.ecombackend.dto.responses.OrderResponseDto;
import ecommerce.ecombackend.enums.OrderStatus;
import ecommerce.ecombackend.enums.PaymentStatus;
import ecommerce.ecombackend.model.*;
import ecommerce.ecombackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public OrderResponseDto placeOrder(OrderRequestDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .paymentMode(dto.getPaymentMode())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (var itemDto : dto.getItems()) {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double itemPrice = product.getPrice() * itemDto.getQuantity();

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(itemPrice)
                    .order(order)
                    .build();

            orderItems.add(item);
            total += itemPrice;
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepo.save(order);

        return toResponse(savedOrder);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepo.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        if (status == OrderStatus.DELIVERED) {
            order.setDeliveryDate(LocalDateTime.now());
            order.setPaymentStatus(PaymentStatus.PAID);
        }

        Order updated = orderRepo.save(order);
        return toResponse(updated);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        orderRepo.delete(order); // cascade delete items
    }

    private OrderResponseDto toResponse(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUser().getId())
                .paymentMode(order.getPaymentMode().name())
                .paymentStatus(order.getPaymentStatus().name())
                .items(itemDtos)
                .build();
    }
}

