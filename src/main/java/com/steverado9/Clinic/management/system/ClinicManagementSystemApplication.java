package com.steverado9.Clinic.management.system;

import com.steverado9.Clinic.management.system.entity.User;
import com.steverado9.Clinic.management.system.enums.Role;
import com.steverado9.Clinic.management.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringBootApplication
public class ClinicManagementSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagementSystemApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByEmail("isaac.stephen@example.com").isEmpty()) {
			String encodedPassword = passwordEncoder.encode("stephen123");

			User user1 = new User("isaac.stephen@example.com", encodedPassword, Role.ADMIN);
			user1.setEnabled(true);
			userRepository.save(user1);
			System.out.println("Default admin user created sucessfully");
		} else {
			System.out.println("Admin user already exists.");
		}
	}
}
