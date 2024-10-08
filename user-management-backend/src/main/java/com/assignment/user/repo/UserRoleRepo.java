package com.assignment.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.user.entity.UserRole;
import com.assignment.user.entity.UserRole.Type;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer>{
	List<UserRole> findByType(Type type);
	Optional<UserRole> findById(int id);
}
