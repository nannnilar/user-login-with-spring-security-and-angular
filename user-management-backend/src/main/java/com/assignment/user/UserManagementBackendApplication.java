package com.assignment.user;

import com.assignment.user.entity.User;
import com.assignment.user.entity.UserRole;
import com.assignment.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@RequiredArgsConstructor
public class UserManagementBackendApplication implements CommandLineRunner{
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserManagementBackendApplication.class, args);
	}


	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		registerAdmin();
	}

	private void registerAdmin() {
		User user = new User();
		UserRole role = new UserRole();
		role.setType(UserRole.Type.ADMIN);
		role.setName("ADMIN");

		user.setLoginId("admin");
		user.setName("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setComfirmPassword(passwordEncoder.encode("admin"));
		user.setEmail("admin@gmail.com");
		user.setAddress("Yangon");
		user.setRoleId(1);

		user.getRoles().add(role);

		System.out.println("============: " + user);
		userRepo.save(user);
	}
}
