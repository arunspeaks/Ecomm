package ecommerce.ecombackend.dto;


import lombok.*;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UserProfileUpdateDto {


        private String name;


        private String email;


        private String phone;

    }


