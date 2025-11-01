package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.DeleteProductRequest;
import ecommerce.ecombackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteProductRequestRepository extends JpaRepository<DeleteProductRequest, Long>  {
}
