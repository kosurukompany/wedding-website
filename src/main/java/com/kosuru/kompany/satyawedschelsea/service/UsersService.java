package com.kosuru.kompany.satyawedschelsea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosuru.kompany.satyawedschelsea.model.Users;
import com.kosuru.kompany.satyawedschelsea.repository.UsersRepository;

@Service("usersService")
public class UsersService {

	private UsersRepository usersRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UsersService(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public List<Users> findAllUsers() {
		return usersRepository.findAll();
	}

	public Users findUserByEmail(String email) {
		return usersRepository.findByEmail(email.toLowerCase());
	}

	public Users findById(String id) {
		return usersRepository.findById(id);
	}

	public Users saveAdminUser(Users user, String userRole) {
		user.setEmail(user.getEmail().toLowerCase());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		user.setRole(userRole);
		user.setCreatedBy("Admin Registration");
		return usersRepository.save(user);
	}

	public Users saveUser(Users user) {
		return usersRepository.save(user);
	}

	public void delete(Users user) {
		usersRepository.delete(user);

	}
}