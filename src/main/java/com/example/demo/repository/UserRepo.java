package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM user WHERE email=?1", nativeQuery = true)
	User checkEmail(String email);

	@Query(value = "SELECT * FROM user WHERE email=?1 AND password=?2", nativeQuery = true)
	User checkLogin(String email, String password);

}
