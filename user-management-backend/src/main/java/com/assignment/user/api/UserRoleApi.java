package com.assignment.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.user.entity.UserRole;
import com.assignment.user.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class UserRoleApi {
	
	@Autowired
	private final RoleService roleService;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveRole(@RequestBody UserRole userRole){
		UserRole roles = roleService.saveRole(userRole);
		try {
			ResponseEntity.ok(roles);
		} catch (Exception e) {
			ResponseEntity.badRequest();
		}
		return null;
	}
	
	@GetMapping("/all")
	public List<UserRole> findAll(){
		return roleService.getAll();
	}

}
