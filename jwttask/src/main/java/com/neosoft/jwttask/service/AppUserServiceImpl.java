package com.neosoft.jwttask.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neosoft.jwttask.domain.AppUser;
import com.neosoft.jwttask.domain.Role;
import com.neosoft.jwttask.repo.AppUserRepo;
import com.neosoft.jwttask.repo.RoleRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

	private final AppUserRepo appUserRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepo.findByUsername(username);
		if(appUser == null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			log.info("User found in the database: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		appUser.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
	}
	
	@Override
	public AppUser saveAppUser(AppUser appUser) {
		log.info("Saving new user {} to the database", appUser.getName());
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		return appUserRepo.save(appUser);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new {} to the database", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToAppUser(String username, String roleName) {
		log.info("Adding role {} to user {} in the database", roleName, username);
		AppUser appUser = appUserRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		appUser.getRoles().add(role);
	}

	@Override
	public AppUser getAppUser(String username) {
		log.info("Fetching user {} from the database", username);
		return appUserRepo.findByUsername(username);
	}

	@Override
	public List<AppUser> getAppUsers() {
		log.info("Fetching all the users from the database");
		return appUserRepo.findAll();
	}
	
}
