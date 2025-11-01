package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.requests.AddressRequestDto;
import ecommerce.ecombackend.dto.responses.AddressResponseDto;
import ecommerce.ecombackend.model.*;
import ecommerce.ecombackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public AddressResponseDto addAddress(AddressRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // If this is being set as default, unset any existing default address
        if (dto.isDefault()) {
            addressRepository.unsetDefaultAddresses(user.getId());
        }

        Address address = Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .isDefault(dto.isDefault())
                .user(user)
                .build();

        Address saved = addressRepository.save(address);
        return mapToDto(saved);
    }

    public List<AddressResponseDto> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto dto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // If this is being set as default, unset any existing default address
        if (dto.isDefault() && !address.isDefault()) {
            addressRepository.unsetDefaultAddresses(address.getUser().getId());
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setDefault(dto.isDefault());

        return mapToDto(addressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // If deleting default address, you might want to set another as default
        if (address.isDefault()) {
            List<Address> otherAddresses = addressRepository.findByUserId(address.getUser().getId());
            if (!otherAddresses.isEmpty()) {
                Address newDefault = otherAddresses.get(0);
                newDefault.setDefault(true);
                addressRepository.save(newDefault);
            }
        }

        addressRepository.delete(address);
    }

    public AddressResponseDto getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return mapToDto(address);
    }

    @Transactional
    public AddressResponseDto setDefaultAddress(Long userId, Long addressId) {
        // Unset all default addresses for this user
        addressRepository.unsetDefaultAddresses(userId);

        // Set the specified address as default
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        address.setDefault(true);
        return mapToDto(addressRepository.save(address));
    }

    public AddressResponseDto getDefaultAddressByUserId(Long userId) {
        Address address = addressRepository.findDefaultByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No default address found for user"));
        return mapToDto(address);
    }

    private AddressResponseDto mapToDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .isDefault(address.isDefault())
                .userId(address.getUser().getId())
                .build();
    }
}