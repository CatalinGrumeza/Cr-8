package com.Cr_8.servicies;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Admin;
import com.Cr_8.repositories.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public void register(Admin admin) { // Register new user with encrypted password

	        String name = admin.getName();
	        String email = admin.getEmail();
	        String password = passwordEncoder.encode(admin.getPassword());

	        admin.setName(name);
	        admin.setPassword(password);
	        admin.setEmail(email);
	        adminRepo.save(admin);
	    }
	public String passwordChange(Admin admin,String pass,String oldPass,@AuthenticationPrincipal Principal principal) {
		String emailLogged=principal.getName();
		String passLogged=adminRepo.findByEmail(emailLogged).get().getPassword();
		if (!passwordEncoder.matches(oldPass, passLogged)) {
           return "La vecchia password non corrisponde a quella inserita";
        }else {
        	admin.setPassword(passwordEncoder.encode(pass));
        	return "Password cambiata con successo!";
        }
	}
}
