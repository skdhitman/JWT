package com.neosoft.jwttask.service;


import java.util.List;

import com.neosoft.jwttask.domain.AppUser;
import com.neosoft.jwttask.domain.Role;

public interface AppUserService {
	AppUser saveAppUser(AppUser user);
	Role saveRole(Role role);
	
	void addRoleToAppUser(String username, String roleName);
	AppUser getAppUser(String username);
	List<AppUser> getAppUsers();
}
