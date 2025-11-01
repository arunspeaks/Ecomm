package ecommerce.ecombackend.service;

import ecommerce.ecombackend.dto.UserProfileUpdateDto;
import ecommerce.ecombackend.dto.requests.UserRequestDto;
import ecommerce.ecombackend.dto.responses.AddressResponseDto;
import ecommerce.ecombackend.enums.UserRole;
import ecommerce.ecombackend.exception.UserAlreadyExistsException;
import ecommerce.ecombackend.model.*;
import ecommerce.ecombackend.repository.AddressRepository;
import ecommerce.ecombackend.repository.UserRepository;
import ecommerce.ecombackend.dto.responses.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserResponseDto registerUser(UserRequestDto dto) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .role(UserRole.CUSTOMER)
                .build();


        User saved = userRepository.save(user);

        return UserResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .role(saved.getRole().name())
                .build();
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }
    public UserResponseDto loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }
    public UserResponseDto updateProfile(Long userId, UserProfileUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());

        User updated = userRepository.save(user);

        return UserResponseDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .phone(updated.getPhone())
                .role(updated.getRole().name())
                .build();
    }

//    public void changePassword(Long userId, String oldPassword, String newPassword) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Check if old password is correct
//        if (!encoder.matches(oldPassword, user.getPassword())) {
//            throw new RuntimeException("Old password is incorrect");
//        }
//
//        // Update with new encoded password
//        user.setPassword(encoder.encode(newPassword));
//        userRepository.save(user);
//    }



    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }



    public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
        }

    public List<AddressResponseDto> getUserAddresses(Long id) {
        List<Address> addresses = addressRepository.findByUserId(id);

        return addresses.stream()
                .map(address -> AddressResponseDto.builder()
                        .id(address.getId())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(address.getZipCode())
                        .isDefault(address.isDefault())
                        .userId(address.getUser().getId())
                        .build())
                .collect(Collectors.toList());
    }

}
