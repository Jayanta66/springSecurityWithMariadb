package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurity.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByusername(String username);


}
