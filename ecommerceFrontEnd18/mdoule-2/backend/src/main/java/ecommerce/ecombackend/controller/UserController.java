package ecommerce.ecombackend.controller;



import ecommerce.ecombackend.dto.UserProfileUpdateDto;
import ecommerce.ecombackend.dto.requests.LoginRequestDto;
import ecommerce.ecombackend.dto.requests.UserRequestDto;
import ecommerce.ecombackend.dto.responses.LoginResponseDto;
import ecommerce.ecombackend.dto.responses.UserResponseDto;
import ecommerce.ecombackend.exception.UserAlreadyExistsException;
import ecommerce.ecombackend.Jwt.JwtUtil;
import ecommerce.ecombackend.mapper.UserMapper;
import ecommerce.ecombackend.model.User;
import ecommerce.ecombackend.repository.UserRepository;
import ecommerce.ecombackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userDto) throws UserAlreadyExistsException {
        UserResponseDto user = userService.registerUser(userDto);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'SELLER')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
//        try {
//            UserResponseDto user = userService.loginUser(request.getEmail(), request.getPassword());
//            return ResponseEntity.ok(LoginResponseDto.builder()
//                    .message("Login successful")
//                    .user(user)
//                    .build());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(401).body(LoginResponseDto.builder()
//                    .message("Invalid email or password")
//                    .build());
//        }
//    }

    @PostMapping("/login")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpSession session) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//
//            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//
//            return ResponseEntity.ok(
//                    LoginResponseDto.builder()
//                            .message("Login successful")
//                            .user(UserMapper.mapToUserResponseDto(user))
//                            .build()
//            );
//        } catch (AuthenticationException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    LoginResponseDto.builder().message("Invalid email or password").build()
//            );
//        }
//    }

    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtUtil.generateToken(request.getEmail(),user.getRole().name());



            return ResponseEntity.ok(LoginResponseDto.builder()
                    .message("Login successful")
                    .token(token)
                    .user(UserMapper.mapToUserResponseDto(user))
                    .build());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    LoginResponseDto.builder().message("Invalid email or password").build()
            );
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("Logged out successfully.");
    }
}


