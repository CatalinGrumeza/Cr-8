package com.Cr_8.servicies;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Admin;
import com.Cr_8.entities.Role;
import com.Cr_8.exceptions.DuplicateResourceException;
import com.Cr_8.repositories.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MailService mailService;
	public void register(Admin admin) { // Register new user with encrypted password
		
		Optional<Admin> adm = adminRepo.findByEmail(admin.getEmail());
		if(adm.isEmpty()) {
		String name = admin.getName();
	    String email = admin.getEmail();
	    String password = passwordEncoder.encode(admin.getPassword());
	    Role role = admin.getRole();

	    admin.setName(name);
	    admin.setPassword(password);
	    admin.setEmail(email);
	    admin.setRole(role);
	    adminRepo.save(admin);
	    }else {
	    	throw new DuplicateResourceException("Exist admin with this email");
	    }
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
	public String reset(String email) {
		// TODO Auto-generated method stub
	
		Admin admin = adminRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found "));  
			String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
			mailService.sendPasswordEmailRest(email,code);
			admin.setCode(code);
			adminRepo.save(admin);
			return  "Code has been send";
		
	}
	public String setnewPassword(String code, String pass) {
		
		Admin admin = adminRepo.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Code is not valid"));  
			
			admin.setPassword(passwordEncoder.encode(pass));
			admin.setCode();
			adminRepo.save(admin);
			return "password saved";

	}
	public List<Admin> getAllAdmin(){
		return adminRepo.findAll();
	}
		
	
}
