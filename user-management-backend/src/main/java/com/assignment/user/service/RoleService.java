package com.assignment.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.user.entity.UserRole;
import com.assignment.user.repo.UserRoleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	
	private final UserRoleRepo userRoleRepo;
	
	public UserRole saveRole(UserRole userRole) {
		return userRoleRepo.save(userRole);
	}
	
	public List<UserRole> getAll(){
		return userRoleRepo.findAll();
	}

}
