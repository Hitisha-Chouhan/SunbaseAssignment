package com.sunbase.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sunbase.assignment.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);

}
