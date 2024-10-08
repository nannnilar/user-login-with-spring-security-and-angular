package com.assignment.user.dto;

import com.assignment.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private String loginId;
    private String password;
    private String comfirmPassword;
    private String name;
    private String email;
    private String address;
    private int roleId;

    public static RegistrationDto entityToDto(User user) {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setLoginId(user.getLoginId());
        registrationDto.setPassword(user.getPassword());
        registrationDto.setComfirmPassword(user.getComfirmPassword());
        registrationDto.setName(user.getName());
        registrationDto.setEmail(user.getEmail());
        registrationDto.setAddress(user.getAddress());
        registrationDto.setRoleId(user.getRoleId());
        return registrationDto;
    }

    public static User dtoToEntity(RegistrationDto registrationDto) {
        User user = new User();
        user.setLoginId(registrationDto.getLoginId());
        user.setPassword(registrationDto.getPassword());
        user.setComfirmPassword(registrationDto.getComfirmPassword());
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setAddress(registrationDto.getAddress());
        user.setRoleId(registrationDto.getRoleId());
        return user;
    }
}
