package com.assignment.user.dto;

import com.assignment.user.entity.User;

import com.assignment.user.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	
	private User user;
	private String jwt;

	public LoginResponseDto(String invalidCredentials) {
	}
}
