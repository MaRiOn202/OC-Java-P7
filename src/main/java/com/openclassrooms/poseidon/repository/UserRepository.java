package com.openclassrooms.poseidon.repository;

import com.openclassrooms.poseidon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);
}
