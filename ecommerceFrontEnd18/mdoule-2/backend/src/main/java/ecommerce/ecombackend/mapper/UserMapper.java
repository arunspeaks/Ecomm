package ecommerce.ecombackend.mapper;

import ecommerce.ecombackend.dto.responses.UserResponseDto;
import ecommerce.ecombackend.model.User;

public class UserMapper {

        public static UserResponseDto mapToUserResponseDto(User user) {
            if (user == null) return null;

            return UserResponseDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .build();
        }
    }


