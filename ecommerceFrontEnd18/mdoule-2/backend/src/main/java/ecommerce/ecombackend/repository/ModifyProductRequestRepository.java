package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.ModifyProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifyProductRequestRepository extends JpaRepository<ModifyProductRequest, Long> {
}
