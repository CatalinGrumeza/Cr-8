//package com.Cr_8.servicies;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.Cr_8.entities.Admin;
//import com.Cr_8.repositories.AdminRepo;
//
//@Service
//public class AdminDetailsService implements UserDetailsService{
//	@Autowired
//	private AdminRepo adminRepo;
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Admin admin = adminRepo.findByEmail(email).orElseThrow(() ->
//        new UsernameNotFoundException("Admin non trovato"));
//	return User.builder()
//            .username(admin.getEmail())
//            .password(admin.getPassword())
//            //.roles("ADMIN")
//            .build();
//
//	}
//
//}
