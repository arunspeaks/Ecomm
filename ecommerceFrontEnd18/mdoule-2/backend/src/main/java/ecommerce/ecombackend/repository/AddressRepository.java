package ecommerce.ecombackend.repository;

import ecommerce.ecombackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import ecommerce.ecombackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Find all addresses for a specific user
    List<Address> findByUserId(Long userId);

    // Find the default address for a user
    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isDefault = true")
    Optional<Address> findDefaultByUserId(@Param("userId") Long userId);

    // Unset all default addresses for a user (used before setting a new default)
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.id = :userId")
    void unsetDefaultAddresses(@Param("userId") Long userId);

    // Optional: Find addresses by user ID and default status
    List<Address> findByUserIdAndIsDefault(Long userId, boolean isDefault);

    // Optional: Check if an address exists for a specific user
    boolean existsByIdAndUserId(Long addressId, Long userId);

    // Optional: Count addresses for a user
    long countByUserId(Long userId);
}
