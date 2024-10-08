package com.assignment.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.user.entity.User;
import com.assignment.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeApi {
	@Autowired
	private final AuthService authService;
	
	@GetMapping("/")
	 public String helloFromHome() {
		 return "Hello from home controller!";
	 }
	
//	@PostMapping("/register")
//	public ResponseEntity<String> registerAdminWithRole() {
//	    try {
//	        authService.registerAdmin();
//	        return ResponseEntity.ok("User saved successfully");
//	    } catch (Exception e) {
//	        return ResponseEntity.badRequest().body("Failed to save user: " + e.getMessage());
//	    }
//	}
}
