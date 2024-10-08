package com.assignment.user.service;

import java.util.List;
import java.util.Optional;

import com.assignment.user.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.user.dto.LoginResponseDto;
import com.assignment.user.entity.User;
import com.assignment.user.entity.UserRole;
import com.assignment.user.repo.UserRepo;
import com.assignment.user.repo.UserRoleRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final UserRepo userRepo;
	@Autowired
	private final UserRoleRepo roleRepo;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private final TokenService tokenService;
	
	public List<User> findAll(){
		return userRepo.findAll();
	}
	@Transactional
	public User updateUser(User updatedUser) {
		Optional<User> existingUserOptional = userRepo.findById(updatedUser.getId());

		if (existingUserOptional.isPresent()) {
			User existingUser = existingUserOptional.get();

			existingUser.setLoginId(updatedUser.getLoginId());
			existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setAddress(updatedUser.getAddress());

			Optional<UserRole> roleOptional = roleRepo.findById(updatedUser.getRoleId());
			if (roleOptional.isPresent()) {
				UserRole role = roleOptional.get();
				existingUser.getRoles().clear();
				existingUser.getRoles().add(role);
			} else {
				throw new RuntimeException("UserRole not found for ID: " + updatedUser.getRoleId());
			}
			return userRepo.save(existingUser);
		} else {
			throw new RuntimeException("User not found for ID: " + updatedUser.getId());
		}
	}
	@Transactional
	public void deleteUser(int userId) {
		try {
			Optional<User> userOptional = userRepo.findById(userId);

			if (userOptional.isPresent()) {
				User userToDelete = userOptional.get();
				userRepo.delete(userToDelete);
			} else {
				throw new RuntimeException("User not found for ID: " + userId);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to delete user: " + e.getMessage());
		}
	}
	public Optional<User> findById(int userId) {
		return userRepo.findById(userId);
	}

	public Page<User> getAllUserByPage(int page, int size, String searchKey, Integer numericValue) {
		PageRequest pageRequest = PageRequest.of(page, size);

		if (searchKey != null && !searchKey.isEmpty()) {
			return userRepo.searchUsers(searchKey, numericValue, pageRequest);
		} else {
			return userRepo.findAll(pageRequest);
		}
	}

		public Page<User> search(int page, int size, Optional<String> searchKey) {
		Integer numericValue = null;
		if (searchKey.isPresent()) {
			String searchStr = searchKey.get();
			try {
				numericValue = Integer.parseInt(searchStr);
			} catch (NumberFormatException ignored) {
			}
		}
		Page<User> users = userRepo.searchUsers(searchKey.orElse("").toLowerCase(), numericValue,PageRequest.of(page, size));
		return users;
	}
	@Transactional
	public User saveUser(RegistrationDto registrationDto) {
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
	
//	public LoginResponseDto loginUser(String loginId, String password){
//
//        try{
//            Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginId, password)
//            );
//
//            String token = tokenService.generateJwt(auth);
//
//            return new LoginResponseDto(userRepo.findByLoginId(loginId).get(), token);
//
//        } catch(AuthenticationException e){
//        	e.printStackTrace();
//            return new LoginResponseDto(null, "");
//        }
//    }
//
//
	

}


