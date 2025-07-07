//package com.car.payload;
//package com.car.dto;
//
//import com.car.entity.UserType;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//public class UserDto {
//
//
//        @NotBlank(message = "Name is required")
//        private String name;
//
//        @NotBlank(message = "Email is required")
//        @Email(message = "Invalid email format")
//        private String email;
//
//        @NotNull(message = "User type is required")
//        private UserType userType;
//
//        public UserDTO() {
//        }
//
//        public UserDTO(String name, String email, UserType userType) {
//            this.name = name;
//            this.email = email;
//            this.userType = userType;
//        }
//
//        // Getters and setters
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public UserType getUserType() {
//            return userType;
//        }
//
//        public void setUserType(UserType userType) {
//            this.userType = userType;
//        }
//    }
//
//}
