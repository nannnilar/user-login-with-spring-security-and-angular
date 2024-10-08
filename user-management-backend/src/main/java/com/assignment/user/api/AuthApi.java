package com.assignment.user.api;

import java.util.List;
import java.util.Optional;

import com.assignment.user.dto.LoginResponseDto;
import com.assignment.user.dto.RegistrationDto;
import com.assignment.user.entity.UserRole;
import com.assignment.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.assignment.user.entity.User;
import com.assignment.user.service.TokenService;
import com.assignment.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi {
	@Autowired
	private final AuthService authService;
	@Autowired
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;	
//	@PostMapping("/register")
	public ResponseEntity<String> registerUserWithRole(@RequestBody RegistrationDto body) {
	    try {
	        authService.register(body);
	        return ResponseEntity.ok("User saved successfully. "+ body);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Failed to save user: " + e.getMessage());
	    }
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> loginUser(@RequestBody User user) {
		try {
			LoginResponseDto responseDto = authService.loginUser(user.getLoginId(), user.getPassword());
			return ResponseEntity.ok(responseDto);
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(null, "Invalid loginId or password"));
		}
	}
//	@PostMapping("/login")
//	public LoginResponseDto loginUser(@RequestBody User user){
//		return authService.loginUser(user.getLoginId(), user.getPassword());
//	}
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		authService.logout();
		return ResponseEntity.ok("Logout successful");
	}

//	@PostMapping("/login")
//	public ResponseEntity<LoginResponseDto> login(@RequestBody RegistrationDto body) {
//		try {
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(body.getLoginId(), body.getPassword())
//			);
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//
//			// Generate JWT and send it in the response header
//			String token = tokenService.generateJwt(authentication);
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Authorization", "Bearer " + token);
//			LoginResponseDto responseDto = new LoginResponseDto(body, token);
//			return ResponseEntity.ok()
//					.headers(headers)
//					.body( responseDto);
//		} catch (AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto("Invalid credentials"));
//		}
//	}


//	@PostMapping("/login")
//	public ResponseEntity<String> loginAnother(@RequestBody User user) {
//		try {
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword())
//			);
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//
//			String token = tokenService.generateJwt(authentication);
//			System.out.println("Roles: "+ authentication.getAuthorities());
//			System.out.println("Authentication: " + authentication);
//			return ResponseEntity.ok("Login successful. Token: " + token);
//
//		} catch (AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//		}
//	}
	@PostMapping("/loginv2")
	public ResponseEntity<String> loginUserV2(@RequestBody RegistrationDto body) {
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(body.getLoginId(), body.getPassword())
	    );

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String token = tokenService.generateJwt(authentication);

	    return ResponseEntity.ok(token);
	}

	@PostMapping("/loginv1")
	public ResponseEntity<String> loginV1(@RequestBody User user) {
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword())
	    );
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    System.out.println("Roles: "+ authentication.getAuthorities());

	    return ResponseEntity.ok("Login successful "+ "\n" + authentication );
	    
	}

	@GetMapping("/type/{type}")
	public ResponseEntity<?> getUserByType(@PathVariable UserRole.Type type) {
		try {
			List<UserRole> userRoles = authService.getUserByType(type);

			if (!userRoles.isEmpty()) {
				return ResponseEntity.ok(userRoles);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error retrieving users by type");
		}
	}







}
