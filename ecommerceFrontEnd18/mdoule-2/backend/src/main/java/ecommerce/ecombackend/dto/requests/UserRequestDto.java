package ecommerce.ecombackend.dto.requests;

import ecommerce.ecombackend.enums.UserRole;


import lombok.*;

@Data
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
}
