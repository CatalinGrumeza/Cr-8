package com.Cr_8.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Role;
import com.Cr_8.repositories.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo;
	
	public Role getByName(String name) {
		Role role = roleRepo.findByName(name).get();
		if (role == null) {
			throw new IllegalArgumentException("Invalid role name");
		}
		return role;
	}
	
}
