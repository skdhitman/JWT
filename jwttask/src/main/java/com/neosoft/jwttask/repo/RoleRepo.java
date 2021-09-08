package com.neosoft.jwttask.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neosoft.jwttask.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
