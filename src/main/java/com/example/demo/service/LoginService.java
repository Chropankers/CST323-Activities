package com.example.demo.service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	public String loginUser(LoginDTO loginDTO) {
	    Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
	    if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword())) {
	        return "Login successful!";
	    } else {
	        return "Invalid credentials.";
	    }
	}

}
