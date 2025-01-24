package com.Cr_8.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Role;
import com.Cr_8.repositories.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo; // Injecting the Role repository
	
	/**
	 * Retrieves a Role by its name.
	 * 
	 * @param name Name of the role
	 * @return The Role object if found
	 * @throws IllegalArgumentException if the role is not found
	 */
	public Role getByName(String name) {
		Optional<Role> role = roleRepo.findByName(name); // Fetch role by name
		if (role.isEmpty()) {
			throw new IllegalArgumentException("Invalid role name"); // Throw exception if role not found
		}
		return role.get(); // Return the role
	}
}
