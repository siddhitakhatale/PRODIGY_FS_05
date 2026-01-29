package com.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.internship.Repository.UserRepo;
import com.internship.config.JwtUtil;
import com.internship.model.LoginRequest;
import com.internship.model.LoginResponse;
import com.internship.model.User;

@Service
public class AuthService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public void register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		
	}

	public LoginResponse login(LoginRequest request) {
		User user=userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("user not found"));
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("invalid credentials");
		}
		String token=jwtUtil.generaterToken(user.getUsername());
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setUsername(user.getUsername());
		loginResponse.setToken(token);
		return loginResponse;
	}

}
