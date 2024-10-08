package com.assignment.user.service;

import com.assignment.user.dto.LoginResponseDto;
import com.assignment.user.dto.RegistrationDto;
import com.assignment.user.repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.user.entity.User;
import com.assignment.user.entity.UserRole;
import com.assignment.user.entity.UserRole.Type;
import com.assignment.user.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	private final UserRepo userRepo;
	@Autowired
	private final UserRoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private final TokenService tokenService;

	@Transactional
	public User register(RegistrationDto registrationDto) {
		Optional<UserRole> roleOptional = roleRepo.findById(registrationDto.getRoleId());

		if (roleOptional.isPresent()) {
			UserRole role = roleOptional.get();
			User user = new User();

			user.setLoginId(registrationDto.getLoginId());
			user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
			user.setComfirmPassword(passwordEncoder.encode(registrationDto.getComfirmPassword()));
			user.setName(registrationDto.getName());
			user.setEmail(registrationDto.getEmail());
			user.setAddress(registrationDto.getAddress());
			user.setRoleId(registrationDto.getRoleId());

			user.getRoles().clear();
			user.getRoles().add(role);

			return userRepo.save(user);
		} else {
			throw new RuntimeException("UserRole not found for ID: " + registrationDto.getRoleId());
		}
	}
		public LoginResponseDto loginUser(String loginId, String password){

            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password)
            );
            String token = tokenService.generateJwt(auth);

            return new LoginResponseDto(userRepo.findByLoginId(loginId).get(), token);


    }


	@Transactional
	public void registerWithUser(User user) {
		Optional<UserRole> roleOptional = roleRepo.findById(user.getRoleId());
		if (roleOptional.isPresent()) {
			UserRole role = roleOptional.get();
			user.getRoles().clear();
			user.getRoles().add(role);

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepo.save(user);
		} else {
			throw new RuntimeException("UserRole not found for ID: " + user.getRoleId());
		}
	}
	public void logout() {
		SecurityContextHolder.clearContext();
	}

	public List<UserRole> getUserByType(Type type) {
		return roleRepo.findByType(type);
	}



}
