package com.Cr_8.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Role;
import com.Cr_8.repositories.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo;
	
	public Role getByName(String name) {
		Optional<Role> role = roleRepo.findByName(name);
		if (role.isEmpty()) {
			throw new IllegalArgumentException("Invalid role name");
		}
		return role.get();
	}
	
}
