package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.AddProductRequest;
import ecommerce.ecombackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddProductRequestRepository extends JpaRepository<AddProductRequest, Long> {
    // Additional custom query methods
}
