package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.Order;
import ecommerce.ecombackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
