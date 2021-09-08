package com.neosoft.jwttask;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.neosoft.jwttask.domain.AppUser;
import com.neosoft.jwttask.domain.Role;
import com.neosoft.jwttask.service.AppUserService;

@SpringBootApplication
public class JwttaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwttaskApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			appUserService.saveRole(new Role(null, "ROLE_USER"));
			appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			
			appUserService.saveAppUser(new AppUser(null, "Roman Reigns", "bigdog", "1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null, "AJ Styles", "phenom", "1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null, "Bill Goldberg", "baddest", "1234", new ArrayList<>()));
			appUserService.saveAppUser(new AppUser(null, "Dave Batista", "animal", "1234", new ArrayList<>()));
			
			appUserService.addRoleToAppUser("phenom", "ROLE_USER");
			appUserService.addRoleToAppUser("animal", "ROLE_MANAGER");
			appUserService.addRoleToAppUser("baddest", "ROLE_ADMIN");
			appUserService.addRoleToAppUser("baddest", "ROLE_MANAGER");
			appUserService.addRoleToAppUser("bigdog", "ROLE_SUPER_ADMIN");
			appUserService.addRoleToAppUser("bigdog", "ROLE_ADMIN");
			appUserService.addRoleToAppUser("bigdog", "ROLE_USER");
		};
	}
}
