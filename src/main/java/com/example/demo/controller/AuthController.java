package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	// =========================
	// RESPONSE CLASS (STEP 3)
	// =========================

	public static class AuthResponse {
		private String message;
		private String role;

		public AuthResponse(String message, String role) {
			this.message = message;
			this.role = role;
		}

		public String getMessage() {
			return message;
		}

		public String getRole() {
			return role;
		}
	}

	// ===================
	// REGISTER
	// ===================
	@PostMapping("/register")
	public String register(@RequestBody User user) {

		User existing = userRepository.findByUsername(user.getUsername());

		if (existing != null) {
			return "Username already exists";
		}

		if (user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("USER");
		}

		userRepository.save(user);
		return "Register success";
	}

	// ====================
	// LOGIN
	// ====================
	@PostMapping("/login")
	public AuthResponse login(@RequestBody User user) {
		User found = userRepository.findByUsername(user.getUsername());

		if (found == null) {
			return new AuthResponse("User not found", null);
		}

		if (!found.getPassword().equals(user.getPassword())) {
			return new AuthResponse("Wrong password", null);
		}

		return new AuthResponse("Login success", found.getRole());
	}
}