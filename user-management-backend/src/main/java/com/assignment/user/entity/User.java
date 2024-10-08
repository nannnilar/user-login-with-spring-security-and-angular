package com.assignment.user.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.assignment.user.security.SecurityUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String loginId;
	private String password;
	private String comfirmPassword;
	private String name;
	private String email;
	private String address;
	
//	@Transient
    private int roleId; 
	
	 @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	 @JoinTable(
	        name = "users_roles",
	        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	    )
    private Set<UserRole> roles = new HashSet<>();



}
