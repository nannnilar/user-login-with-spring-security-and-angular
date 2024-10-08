package com.assignment.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByLoginId(String loginId);

	@Query("select u from User u where " +
			"u.loginId like %:searchKey% " +
//			"or u.password like %:searchKey% " +
			"or u.name like %:searchKey% " +
			"or u.email like %:searchKey% " +
			"or u.address like %:searchKey% " +
//			"or u.roleId = :numericValue " +
			"or u.id = :numericValue " +
			"or (:numericValue IS NOT NULL AND u.id <= :numericValue)")
	Page<User> searchUsers(@Param("searchKey") String searchKey, @Param("numericValue") Integer numericValue, PageRequest pageRequest);

}
