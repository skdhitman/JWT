package com.neosoft.jwttask.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neosoft.jwttask.domain.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);
}
