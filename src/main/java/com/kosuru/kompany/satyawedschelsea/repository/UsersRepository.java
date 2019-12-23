package com.kosuru.kompany.satyawedschelsea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosuru.kompany.satyawedschelsea.model.Users;

@Repository("usersRepository")
public interface UsersRepository extends JpaRepository<Users, Long> {

	@Override
	List<Users> findAll();

	Users findByEmail(String email);

	Users findById(String id);
}
